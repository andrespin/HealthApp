package android.andrespin.healthapp.ui

data class NoteData(
    val time: String,
    val date: String,
    val upperPressure: Int,
    val lowerPressure: Int,
    val pulse: Int
)