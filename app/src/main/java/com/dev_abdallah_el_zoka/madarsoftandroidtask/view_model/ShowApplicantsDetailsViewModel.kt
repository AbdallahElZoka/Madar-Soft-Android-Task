package com.dev_abdallah_el_zoka.madarsoftandroidtask.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_abdallah_el_zoka.madarsoftandroidtask.repo.ApplicantsRepository
import com.dev_abdallah_el_zoka.madarsoftandroidtask.intent.FetchApplicantsIntent
import com.dev_abdallah_el_zoka.madarsoftandroidtask.state.FetchApplicantsState
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
class ShowApplicantsDetailsViewModel @Inject constructor(val applicantsRepository: ApplicantsRepository) :
    ViewModel() {

    val userIntent = Channel<FetchApplicantsIntent>(Channel.CONFLATED)
    private val _state = MutableStateFlow<FetchApplicantsState>(FetchApplicantsState.Idle)
    val applicantsState: StateFlow<FetchApplicantsState> get() = _state

    init {
        handleIntent()
    }

    @InternalCoroutinesApi
    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is FetchApplicantsIntent.FetchApplicants -> getApplicantsListFromDatabase()
                }
            }

        }
    }

    fun getApplicantsListFromDatabase() {
        viewModelScope.launch {
            _state.value = FetchApplicantsState.Loading
            _state.value = try {
                val responseFromDataBase =
                    applicantsRepository.getApplicantsFromDatabase()
                FetchApplicantsState.Applicants(responseFromDataBase)
            } catch (e: Exception) {
                FetchApplicantsState.Error(e.localizedMessage!!)

            }
        }

    }
}