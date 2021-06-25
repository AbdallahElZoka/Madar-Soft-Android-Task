package com.dev_abdallah_el_zoka.madarsoftandroidtask.state

import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.pojo.ApplicantModel

sealed class FetchApplicantsState {
    object Idle : FetchApplicantsState()
    object Loading : FetchApplicantsState()
    data class Applicants(val applicants: List<ApplicantModel>) : FetchApplicantsState()
    data class Error(val error: String?) : FetchApplicantsState()

}
