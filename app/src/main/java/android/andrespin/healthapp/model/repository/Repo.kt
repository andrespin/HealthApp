package android.andrespin.healthapp.model.repository

import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.database.NoteDao
import android.andrespin.healthapp.model.database.NoteEntity
import android.andrespin.healthapp.utils.converter.IConverter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val provideNoteDao: NoteDao,
    private val provideConverter: IConverter,
) : IRepo {

    override suspend fun getAllNotes(): List<NoteEntity> = provideNoteDao.getAllNotes()

    override suspend fun deleteDayNotes(dayNote: DayNotes) {
        val convertedToNoteEntityList = provideConverter.convertToNoteEntityList(dayNote)
        for (element in convertedToNoteEntityList) {
            provideNoteDao.deleteNote(
                element
            )
        }
    }

    override suspend fun deleteNote(note: Note) {
        val convertedToNoteEntity = provideConverter.convertToNoteEntity(note)
        provideNoteDao.deleteNote(
            convertedToNoteEntity
        )
    }

    override suspend fun insertNote(noteEntity: NoteEntity) =
        provideNoteDao.insertNote(noteEntity)


}