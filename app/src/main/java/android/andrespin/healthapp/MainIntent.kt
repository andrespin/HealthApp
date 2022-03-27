package android.andrespin.healthapp

import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.NoteData

sealed class MainIntent {
    object DeleteAllNotes : MainIntent()
    data class DeleteDayNotes(val dayNotes: DayNotes) : MainIntent()
    data class DeleteNote(val note: Note) : MainIntent()
    data class SaveAndDisplay(val data: NoteData) : MainIntent()
}
