package android.andrespin.healthapp.model

import android.andrespin.healthapp.R

class GetData {

    private val noteDataList = listOf(
        NoteData(
            "17:55",
            "",
            "123",
            "76",
            "56",
            R.drawable.back_note_item_data_yellow
        ),
        NoteData(
            "17:55",
            "",
            "123",
            "76",
            "56",
            R.drawable.back_note_item_data_yellow
        )
    )

    private val list = arrayListOf(
        DayNotes(
            "13 февраля",
            noteDataList
        ),
        DayNotes("14 февраля"),
        DayNotes(
            "15 февраля",
            noteDataList
        ),
        DayNotes("16 февраля")
    )

    fun getData() = list


}