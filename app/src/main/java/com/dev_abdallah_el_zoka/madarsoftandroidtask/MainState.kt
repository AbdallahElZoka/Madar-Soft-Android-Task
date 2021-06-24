package com.dev_abdallah_el_zoka.madarsoftandroidtask

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    object Done : MainState()
    data class Error(val error: String?) : MainState()
}
