package com.dev_abdallah_el_zoka.madarsoftandroidtask.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_abdallah_el_zoka.madarsoftandroidtask.Navigator
import com.dev_abdallah_el_zoka.madarsoftandroidtask.intent.SaveApplicantsIntent
import com.dev_abdallah_el_zoka.madarsoftandroidtask.state.SaveApplicantsState
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel
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

    val nameErrorStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val jobTitleErrorStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val ageErrorStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

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
            nameErrorStateFlow.value = true
            jobTitleErrorStateFlow.value = false
            ageErrorStateFlow.value = false
            return false
        } else if (jobTitle.isNullOrEmpty() || jobTitle.isNullOrBlank()) {
            nameErrorStateFlow.value = false
            jobTitleErrorStateFlow.value = true
            ageErrorStateFlow.value = false
            return false
        } else if (age.isNullOrEmpty() || age.isNullOrBlank()) {
            nameErrorStateFlow.value = false
            jobTitleErrorStateFlow.value = false
            ageErrorStateFlow.value = true
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