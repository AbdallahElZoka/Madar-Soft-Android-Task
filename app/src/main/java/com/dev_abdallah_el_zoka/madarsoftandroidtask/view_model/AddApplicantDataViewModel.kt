package com.dev_abdallah_el_zoka.madarsoftandroidtask.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_abdallah_el_zoka.madarsoftandroidtask.Navigator
import com.dev_abdallah_el_zoka.madarsoftandroidtask.intent.SaveApplicantsIntent
import com.dev_abdallah_el_zoka.madarsoftandroidtask.state.SaveApplicantsState
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.InputModel
import com.dev_abdallah_el_zoka.madarsoftandroidtask.repo.ApplicantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class AddApplicantDataViewModel @Inject constructor(val applicantsRepository: ApplicantsRepository) :
    ViewModel() {
    val symbols = "0123456789/?!:;%@"
    val nameErrorStateFlow: MutableStateFlow<InputModel> =
        MutableStateFlow(InputModel(data = null, blankError = false, containSymbolsError = false))
    val jobTitleErrorStateFlow: MutableStateFlow<InputModel> =
        MutableStateFlow(InputModel(data = null, blankError = false, containSymbolsError = false))
    val ageErrorStateFlow: MutableStateFlow<InputModel> =
        MutableStateFlow(InputModel(data = null, blankError = false, containSymbolsError = false))

    var navigator: Navigator? = null

    val userIntent = Channel<SaveApplicantsIntent>(Channel.CONFLATED)
    private val _state = MutableStateFlow<SaveApplicantsState>(SaveApplicantsState.Idle)
    val applicantsState: StateFlow<SaveApplicantsState> get() = _state


    @InternalCoroutinesApi
    private fun handleIntent(applicantModel: ApplicantModel) {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is SaveApplicantsIntent.SaveApplicants -> saveApplicantInDatabase(applicantModel = applicantModel)

                }
            }

        }
    }

    fun saveApplicantInDatabase(applicantModel: ApplicantModel) {
        viewModelScope.launch {
            _state.value = SaveApplicantsState.Loading
            _state.value = try {
                applicantsRepository.saveApplicantsInDatabase(applicantModel = applicantModel)
                SaveApplicantsState.Done
            } catch (e: Exception) {
                SaveApplicantsState.Error(e.localizedMessage!!)

            }
        }
    }

    fun validateUserInput(name: String?, jobTitle: String?, age: String?, gender: String): Boolean {
        if (name.isNullOrEmpty() || name.isNullOrBlank()) {
            nameErrorStateFlow.value =
                InputModel(data = name, blankError = true, containSymbolsError = false)
            jobTitleErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            ageErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            return false
        } else if (name.any { it in symbols }) {
            nameErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = true)
            jobTitleErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            ageErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            return false

        } else if (jobTitle.isNullOrEmpty() || jobTitle.isNullOrBlank()) {
            nameErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            jobTitleErrorStateFlow.value =
                InputModel(data = name, blankError = true, containSymbolsError = false)
            ageErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            return false
        } else if (jobTitle.any { it in symbols }) {
            nameErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            jobTitleErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = true)
            ageErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            return false
        } else if (age.isNullOrEmpty() || age.isNullOrBlank() || age.toInt() !in 10..60) {
            nameErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            jobTitleErrorStateFlow.value =
                InputModel(data = name, blankError = false, containSymbolsError = false)
            ageErrorStateFlow.value = InputModel(data = name, blankError = true)
            return false
        }
        viewModelScope.launch {
            userIntent.send(SaveApplicantsIntent.SaveApplicants)
        }
        handleIntent(
            ApplicantModel(
                applicantName = name,
                jobTitle = jobTitle,
                gender = gender,
                age = age.toInt()
            )
        )
        return true
    }

    fun navigateToShowApplicantsDetailsActivity() {
        navigator?.navigateToShowApplicantsDetailsActivity()
    }


}