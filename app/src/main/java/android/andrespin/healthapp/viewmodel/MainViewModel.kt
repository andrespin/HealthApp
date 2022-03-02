package android.andrespin.healthapp.viewmodel

import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.MainState
import android.andrespin.healthapp.model.GetData
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

    private fun setStateValue(value: MainState) {
        _state.value = value
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddNote -> addNote()
                }
            }
        }
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