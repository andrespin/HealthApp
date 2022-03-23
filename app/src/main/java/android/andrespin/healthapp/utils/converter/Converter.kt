package android.andrespin.healthapp.utils.converter

import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.database.NoteEntity

class Converter : ConverterTools(), IConverter {

    override fun convertToDayNotes(list: List<Note>) = toDayNotes(list)

    override fun convertToNote(list: List<NoteEntity>) = toNote(list)

}