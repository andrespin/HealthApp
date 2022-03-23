package android.andrespin.healthapp

import android.andrespin.healthapp.model.NoteData

sealed class MainIntent {
    object AddNote : MainIntent()
    object DisplayNotes : MainIntent()
    object DeleteAllNotes : MainIntent()
    data class SaveData(val data: NoteData) : MainIntent()
}
