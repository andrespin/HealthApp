package android.andrespin.healthapp.utils.converter

import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.database.NoteEntity

interface IConverter {

    fun convertToDayNotes(list: List<Note>): List<DayNotes>

    fun convertToNote(list: List<NoteEntity>): List<Note>

}