package android.andrespin.healthapp.model.database

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE number=:number")
    suspend fun getNote(number: Int): NoteEntity

}

