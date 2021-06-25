package com.dev_abdallah_el_zoka.madarsoftandroidtask.state

sealed class SaveApplicantsState {
    object Idle : SaveApplicantsState()
    object Loading : SaveApplicantsState()
    object Done : SaveApplicantsState()
    data class Error(val error: String?) : SaveApplicantsState()
}
