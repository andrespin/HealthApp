package android.andrespin.healthapp.viewmodel

import android.andrespin.healthapp.EventState
import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.MainState
import android.andrespin.healthapp.R
import android.andrespin.healthapp.model.GetData
import android.andrespin.healthapp.model.NoteData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val intent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    private val _eventState = MutableStateFlow<EventState>(EventState.Idle)
    val eventState: StateFlow<EventState> get() = _eventState


    private fun setStateValue(value: MainState) {
        _state.value = value
    }

    private fun setEventState(event: EventState) {
        _eventState.value = event
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNote -> addNote()
                    is MainIntent.SaveData -> saveData(it)
                }
            }
        }
    }

    private fun saveData(it: MainIntent.SaveData) {
        setNoteBackgroundColor(it.data)
        // TODO сохранение на удалённом источнике
    }

    private fun setNoteBackgroundColor(data: NoteData): NoteData {
        val lowerPressure = data.lowerPressure!!.toInt()
        val upperPressure = data.upperPressure!!.toInt()
        val back: Int
        if (lowerPressure < 60 && upperPressure < 100) {
            back = R.drawable.back_note_item_data_blue
        } else if (lowerPressure in 60..79 && upperPressure in 100..119) {
            back = R.drawable.back_note_item_data_light_green
        } else if (lowerPressure in 80..84 && upperPressure in 120..129) {
            back = R.drawable.back_note_item_data_green
        } else if (lowerPressure in 85..89 && upperPressure in 130..139) {
            back = R.drawable.back_note_item_data_yellow
        } else if (lowerPressure > 90 && upperPressure > 140) {
            back = R.drawable.back_note_item_data_red
        } else {
            back = R.drawable.back_note_item_data_white
        }
        return NoteData(
            data.time,
            data.date,
            data.upperPressure,
            data.lowerPressure,
            data.pulse,
            back
        )
    }

    private fun addNote() {
        viewModelScope.launch {
            setStateValue(MainState.Loading)
            setStateValue(
                try {
                    val data = GetData().getData()
                    MainState.Data(data)
                } catch (e: Exception) {
                    MainState.Error(e.localizedMessage)
                }
            )
        }
    }

}