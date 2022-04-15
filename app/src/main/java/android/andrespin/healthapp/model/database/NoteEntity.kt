package android.andrespin.healthapp.model.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "note_table")
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    var number: Int = 0,

    val time: String?,

    val date: String?,

    val upperPressure: String?,

    val lowerPressure: String?,

    val pulse: String?

)


