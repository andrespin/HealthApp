package android.andrespin.healthapp.model


data class NoteData(
    val time: String,
    val date: String,
    val upperPressure: Int,
    val lowerPressure: Int,
    val pulse: Int,
    val background: Int? = null
)

data class DayNotes(
    val date: String,
    val notes: List<NoteData>? = null
)
