package android.andrespin.healthapp.model

import android.os.Parcel
import android.os.Parcelable

data class NoteData(
    val time: String?,
    val date: String?,
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
    ) {
    }

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

data class DayNotes(
    val date: String,
    val notes: List<NoteData>? = null
)
