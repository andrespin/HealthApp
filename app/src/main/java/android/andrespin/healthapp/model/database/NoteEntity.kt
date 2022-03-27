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
//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(number)
//        parcel.writeString(time)
//        parcel.writeString(date)
//        parcel.writeString(upperPressure)
//        parcel.writeString(lowerPressure)
//        parcel.writeString(pulse)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<NoteEntity> {
//        override fun createFromParcel(parcel: Parcel): NoteEntity {
//            return NoteEntity(parcel)
//        }
//
//        override fun newArray(size: Int): Array<NoteEntity?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

