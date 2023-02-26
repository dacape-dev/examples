package dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDatabase
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.repository.NotesRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): ViewModel() {

    private val repository: NotesRepository

    val all: LiveData<List<Note>>

    var openDialog by mutableStateOf(false)

    private var currentId: Int? = null

    private val _text = mutableStateOf(TextFieldState())
    val text: State<TextFieldState> = _text

    init {
        val db = NotesDatabase.getInstance(application)
        val dao = db.notesDao()
        repository = NotesRepository(dao)

        all = repository.all()
    }

    fun load(id: Int?){
        viewModelScope.launch {
            if (id != null) {
                repository.findById(id)?.also { note ->
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
            onEvent(Event.OpenDialog)
        }
    }

    fun isEdit(): Boolean{
        if(currentId == null){
            return false
        }
        return true
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.SetText -> {
                _text.value = text.value.copy(
                    text = event.value
                )
            }
            is Event.Save -> {
                if(isEdit()){
                    repository.update(Note(currentId, text.value.text, null, null))
                }else{
                    repository.insert(Note(null, text.value.text, null, null))
                }
                openDialog = false
            }
            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
        }
    }

}

sealed class Event {
    data class SetText(val value: String): Event()
    object OpenDialog: Event()
    object CloseDialog: Event()
    object Save: Event()
}

data class TextFieldState(
    val text: String = ""
)