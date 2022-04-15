package android.andrespin.healthapp.viewmodel

import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.MainState
import android.andrespin.healthapp.model.database.NoteDao
import android.andrespin.healthapp.model.database.NoteEntity
import android.andrespin.healthapp.model.repository.IRepo
import android.andrespin.healthapp.utils.converter.IConverter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val provideConverter: IConverter,
    private val provideRepo: IRepo
) : ViewModel() {

    val intent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    private var savedNotes = mutableListOf<NoteEntity>()

    private fun setStateValue(value: MainState) {
        _state.value = value
    }

    init {
        handleData()
        handleIntent()
    }

    private fun handleData() {
        viewModelScope.launch {
            coroutineScope {
                val get = launch(start = CoroutineStart.LAZY) {
                    getSavedNotes()
                }
                val display = launch(start = CoroutineStart.LAZY) {
                    displayNotes()
                }
                get.start()
                get.join()
                display.start()
            }
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.SaveAndDisplay -> saveAndDisplay(it)
                    is MainIntent.DeleteDayNotes -> deleteDayNotes(it)
                    is MainIntent.DeleteNote -> deleteNote(it)
                }
            }
        }
    }

    private fun deleteDayNotes(dayNotes: MainIntent.DeleteDayNotes) {
        println("dayNotes $dayNotes")
        viewModelScope.launch {
            coroutineScope {
                val delete = launch(start = CoroutineStart.LAZY) {
                    provideRepo.deleteDayNotes(dayNotes.dayNotes)
                }

                val get = launch(start = CoroutineStart.LAZY) {
                    getSavedNotes()
                }

                val display = launch(start = CoroutineStart.LAZY) {
                    displayNotes()
                }
                delete.start()
                delete.join()
                get.start()
                get.join()
                display.start()
            }
        }
    }

    private fun deleteNote(note: MainIntent.DeleteNote) {
        println("note $note")
        viewModelScope.launch {
            coroutineScope {
                val delete = launch(start = CoroutineStart.LAZY) {
                    provideRepo.deleteNote(note.note)
                }

                val get = launch(start = CoroutineStart.LAZY) {
                    getSavedNotes()
                }

                val display = launch(start = CoroutineStart.LAZY) {
                    displayNotes()
                }
                delete.start()
                delete.join()
                get.start()
                get.join()
                display.start()
            }
        }
    }

    private fun displayNotes() {
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

    private fun saveAndDisplay(it: MainIntent.SaveAndDisplay) {
        viewModelScope.launch {
            coroutineScope {
                val save = launch(start = CoroutineStart.LAZY) {
                    saveData(it)
                }

                val update = launch(start = CoroutineStart.LAZY) {
                    updateSavedNotes()
                }

                val display = launch(start = CoroutineStart.LAZY) {
                    displayNotes()
                }
                save.start()
                save.join()
                update.start()
                update.join()
                display.start()
            }
        }
    }

    private suspend fun saveData(it: MainIntent.SaveAndDisplay) =
        provideRepo.insertNote(
            NoteEntity(
                0,
                it.data.time,
                it.data.date,
                it.data.upperPressure,
                it.data.lowerPressure,
                it.data.pulse
            )
        )


    private suspend fun updateSavedNotes() {
        savedNotes = provideRepo.getAllNotes() as MutableList<NoteEntity>
    }

    private suspend fun getSavedNotes() {
        savedNotes = provideRepo.getAllNotes() as MutableList<NoteEntity>
    }


}