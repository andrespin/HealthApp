package android.andrespin.healthapp.model.repository

import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.database.NoteEntity

interface IRepo {

    suspend fun getAllNotes(): List<NoteEntity>

    suspend fun deleteDayNotes(dayNote: DayNotes)

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(noteEntity: NoteEntity)

}