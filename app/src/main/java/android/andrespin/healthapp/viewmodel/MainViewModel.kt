package android.andrespin.healthapp.viewmodel

import android.andrespin.healthapp.EventState
import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.MainState
import android.andrespin.healthapp.model.database.NoteDao
import android.andrespin.healthapp.model.database.NoteEntity
import android.andrespin.healthapp.utils.converter.Converter
import android.andrespin.healthapp.utils.converter.IConverter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val provideNoteDao: NoteDao,
    private val provideConverter: IConverter
) : ViewModel() {

    val intent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    private val _eventState = MutableStateFlow<EventState>(EventState.Idle)
    val eventState: StateFlow<EventState> get() = _eventState

    private var savedNotes = mutableListOf<NoteEntity>()

    private fun setStateValue(value: MainState) {
        _state.value = value
    }

    private fun setEventState(event: EventState) {
        _eventState.value = event
    }

    init {
        runBlocking {
            downloadNotesFromDatabase()
        }
        displayNotes()
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.DisplayNotes -> displayNotes()
                    is MainIntent.AddNote -> addNote()
                    is MainIntent.SaveData -> saveData(it)
                    is MainIntent.DeleteAllNotes -> deleteAllNotes()
                }
            }
        }
    }

    private fun deleteAllNotes() {
        viewModelScope.launch {
            provideNoteDao.deleteAllNotes()
            println("notes deleted")
        }
    }

    private fun downloadNotesFromDatabase() {
        viewModelScope.launch {
            savedNotes = provideNoteDao.getAllNotes() as MutableList<NoteEntity>
            println("downloadNotesFromDatabase")
            println("savedNotes $savedNotes")
        }
    }

    private fun displayNotes() {
        viewModelScope.launch {
            setStateValue(MainState.Loading)
            println("savedNotes.size ${savedNotes.size}")
            if (savedNotes.size >= 1) {
                val convertedToNote = provideConverter.convertToNote(savedNotes)
                val convertedToDayNotes = provideConverter.convertToDayNotes(convertedToNote)
                setStateValue(
                    MainState.Data(
                        convertedToDayNotes
                    )
                )
            }
        }
    }

    private fun saveData(it: MainIntent.SaveData) {
        println("saveData $it")
        viewModelScope.launch {
            provideNoteDao.insertNote(
                NoteEntity(
                    0,
                    it.data.time,
                    it.data.date,
                    it.data.upperPressure,
                    it.data.lowerPressure,
                    it.data.pulse
                )
            )
            savedNotes = provideNoteDao.getAllNotes() as MutableList<NoteEntity>
        }

    }


    private fun addNote() {
//        viewModelScope.launch {
//            setStateValue(MainState.Loading)
//            setStateValue(
//                try {
//                    val data = GetData().getData()
//                    MainState.Data(data)
//                } catch (e: Exception) {
//                    MainState.Error(e.localizedMessage)
//                }
//            )
//        }
    }

}