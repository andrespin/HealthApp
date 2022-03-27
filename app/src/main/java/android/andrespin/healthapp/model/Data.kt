package android.andrespin.healthapp.model

import android.os.Parcel
import android.os.Parcelable

data class NoteData(
    val date: String?,
    val time: String?,
    val upperPressure: String?,
    val lowerPressure: String?,
    val pulse: String?,
    val background: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(time)
        parcel.writeString(date)
        parcel.writeString(upperPressure)
        parcel.writeString(lowerPressure)
        parcel.writeString(pulse)
        parcel.writeValue(background)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteData> {
        override fun createFromParcel(parcel: Parcel): NoteData {
            return NoteData(parcel)
        }

        override fun newArray(size: Int): Array<NoteData?> {
            return arrayOfNulls(size)
        }
    }
}

data class Date(var number: Int, var mounth: Int, var year: Int)

data class DayNotes(
    val date: Date,
    var notes: List<Note>
)

data class Note(
    var numberId: Int = 0,
    val time: String?,
    val date: String?,
    val upperPressure: String?,
    val lowerPressure: String?,
    val pulse: String?,
    val background: Int?
)
