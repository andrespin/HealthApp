package android.andrespin.healthapp

import android.andrespin.healthapp.model.DayNotes

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Data(val data: List<DayNotes>) : MainState()
    data class Error(val error: String?) : MainState()
}

