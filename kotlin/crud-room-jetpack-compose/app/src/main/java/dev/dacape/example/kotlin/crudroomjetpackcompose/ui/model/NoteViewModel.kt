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
        }
    }

    fun setName(name: String){
        _text.value = text.value.copy(
            text = name
        )
    }

    fun insert(){
        repository.insert(Note(null, text.value.text, null, null))
    }

    fun update(){
        repository.update(Note(currentId, text.value.text, null, null))
    }

    fun openDialog(){
        openDialog = true
    }

    fun closeDialog(){
        openDialog = false
    }

    fun isEdit(): Boolean{
        if(currentId == null){
            return false
        }
        return true
    }
}

data class TextFieldState(
    val text: String = ""
)