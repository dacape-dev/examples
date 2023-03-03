package dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dacape.example.kotlin.crudroomjetpackcompose.core.TextFieldState
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDatabase
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel(application: Application): ViewModel() {

    private val repository: NotesRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _text = mutableStateOf(TextFieldState())
    val text: State<TextFieldState> = _text

    val all: LiveData<List<Note>>
    var openDialog by mutableStateOf(false)
    private var currentId: Int? = null

    init {
        val db = NotesDatabase.getInstance(application)
        val dao = db.notesDao()
        repository = NotesRepository(dao)

        all = repository.all()
    }

    private fun load(id: Int?){
        viewModelScope.launch {
            if (id != null) {
                repository.findById(id).also { note ->
                    currentId = note.id
                    _text.value = text.value.copy(
                        text = note.text
                    )
                }
            }else{
                currentId = null
                _text.value = text.value.copy(
                    text = "text"
                )
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.SetText -> {
                _text.value = text.value.copy(
                    text = event.text
                )
            }
            is Event.Save -> {
                if(currentId != null){
                    repository.update(Note(currentId, text.value.text, Date()))
                }else{
                    repository.insert(Note(null, text.value.text, Date()))
                }
                openDialog = false
                coroutineScope.launch(Dispatchers.IO) {
                    _eventFlow.emit(Event.Save)
                }
            }
            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
            is Event.Load -> {
                load(event.id)
                openDialog = true
            }
            is Event.Delete -> {
                event.id?.let { repository.delete(it) }
            }
        }
    }
}

