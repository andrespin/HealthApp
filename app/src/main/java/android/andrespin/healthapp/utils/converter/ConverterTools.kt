package android.andrespin.healthapp.utils.converter

import android.andrespin.healthapp.model.Date
import android.andrespin.healthapp.model.DayNotes
import android.andrespin.healthapp.model.Note
import android.andrespin.healthapp.model.database.NoteEntity
import android.andrespin.healthapp.utils.*
import java.lang.NumberFormatException

abstract class ConverterTools {

    protected fun toDayNotes(list: List<Note>): List<DayNotes> {
        val l = mutableListOf<DayNotes>()
        for (i in list.indices) {
            list[i].date?.toCharArray()
            if (list[i].date != null) {
                val date = parseDate(list[i].date!!)
                l.add(
                    DayNotes(
                        date,
                        mutableListOf(list[i])
                    )
                )
            }
        }
        return sortAccordingDate(putNoteListToSameDate(l))
    }

    protected fun toNote(list: List<NoteEntity>): List<Note> {
        val data = mutableListOf<Note>()
        for (i in list.indices) {
            data.add(
                Note(
                    list[i].number,
                    list[i].time,
                    list[i].date,
                    list[i].upperPressure,
                    list[i].lowerPressure,
                    list[i].pulse,
                    getNoteBackgroundColor(list[i])
                )
            )
        }
        return data
    }

    protected fun toNoteEntity(note: Note) =
        NoteEntity(
            note.numberId,
            note.time,
            note.date,
            note.upperPressure,
            note.lowerPressure,
            note.pulse
        )

    protected fun toNoteEntityList(dayNotes: DayNotes): List<NoteEntity> {
        val list = mutableListOf<NoteEntity>()
        for (i in 0 until dayNotes.notes.size) {
            list.add(
                NoteEntity(
                    dayNotes.notes[i].numberId,
                    dayNotes.notes[i].time,
                    dayNotes.notes[i].date,
                    dayNotes.notes[i].upperPressure,
                    dayNotes.notes[i].lowerPressure,
                    dayNotes.notes[i].pulse
                )
            )
        }
        return list
    }

    private fun sortAccordingDate(l: List<DayNotes>): List<DayNotes> {
        val list = l as MutableList<DayNotes>
        val n = list.size
        var temp: DayNotes
        for (i in 0 until n) {
            var indexOfMin = i
            for (j in n - 1 downTo i) {
                if (isDate1Bigger(list[j].date, list[indexOfMin].date)) {
                    indexOfMin = j
                }
            }
            if (i != indexOfMin) {
                temp = list[i]
                list[i] = list[indexOfMin]
                list[indexOfMin] = temp
            }
        }
        return l
    }

    private fun putNoteListToSameDate(list: List<DayNotes>): List<DayNotes> {
        val l = mutableListOf<DayNotes>()
        for (i in 0 until list.size) {
            val data = checkIfContainsSameDateInList(l, list[i])
            if (data.isSameDateInList) {
                l[data.indexOfListWithSameDate].notes += list[i].notes
            } else {
                l.add(list[i])
            }
        }
        return l
    }

    private data class SameDate(
        val isSameDateInList: Boolean,
        val indexOfListWithSameDate: Int = -1
    )

    private fun checkIfContainsSameDateInList(
        listToCheck: List<DayNotes>,
        dayNotes: DayNotes
    ): SameDate {
        if (listToCheck.isEmpty()) return SameDate(false)
        for (i in listToCheck.indices) {
            if (listToCheck[i].date == dayNotes.date) return SameDate(true, i)
        }
        return SameDate(false)
    }

    private fun getNoteBackgroundColor(data: NoteEntity): Int {
        val lowerPressure = data.lowerPressure!!.toInt()
        val upperPressure = data.upperPressure!!.toInt()
        return if (lowerPressure < 60 || upperPressure < 100) {
            blueColorBackground
        } else if (lowerPressure in 60..79 || upperPressure in 100..119) {
            greenColorBackground
        } else if (lowerPressure in 80..84 || upperPressure in 120..129) {
            lightGreenColorBackground
        } else if (lowerPressure in 85..89 || upperPressure in 130..139) {
            yellowColorBackground
        } else if (lowerPressure > 90 || upperPressure > 140) {
            redColorBackground
        } else {
            whiteColorBackground
        }
    }

    private fun parseDate(date: String): Date {

        val array = date.toCharArray()
        var number = ""
        var mounth = ""
        var year = ""

        for (i in array.indices) {
            try {

                when (array[i].toString().toInt()) {
                    in 0..9 -> {
                        if (number.length < 2) {
                            number += array[i]
                        } else if (number.length == 2 && mounth.length < 2) {
                            mounth += array[i]
                        } else if (number.length == 2 && mounth.length >= 2) {
                            year += array[i]
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                if (array[i] in 'а'..'я' || array[i] in 'А'..'Я') {
                    mounth += array[i]
                }
            }
        }

        number.replace(" ", "")
        year.replace(" ", "")
        mounth.replace(" ", "")
        val n = number.toInt()
        val y = year.toInt()
        var m = 0
        m = try {
            mounth.toInt()
        } catch (e: NumberFormatException) {
            mapRussianMonths[mounth]!!
        }
        return Date(n, m, y)
    }

    private fun isDate1Bigger(date1: Date, date2: Date): Boolean {
        if (date1.year > date2.year) {
            return true
        } else if (date1.year == date2.year) {
            if (date1.mounth > date2.mounth) {
                return true
            } else if (date1.mounth == date2.mounth) {
                if (date1.number > date2.number) {
                    return true
                }
            }
        }
        return false
    }
}