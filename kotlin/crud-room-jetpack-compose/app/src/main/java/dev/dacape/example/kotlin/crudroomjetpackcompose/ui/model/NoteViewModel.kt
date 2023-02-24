package dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDatabase
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.repository.NotesRepository

class NoteViewModel(application: Application): ViewModel() {

    private val repository: NotesRepository

    val all: LiveData<List<Note>>

    var openDialog by mutableStateOf(false)

    var note by mutableStateOf( Note(null, "text", null, null))

    init {
        val db = NotesDatabase.getInstance(application)
        val dao = db.notesDao()
        repository = NotesRepository(dao)

        all = repository.all()
    }

    fun new(){
        note = Note(null, "text", null, null)
    }

    fun insert(){
        repository.insert(note)
    }

    fun update(){
        repository.update(note)
    }

    fun openDialog(){
        openDialog = true
    }

    fun closeDialog(){
        openDialog = false
    }

    fun isEdit(): Boolean{
        if(note.id == null){
            return false
        }
        return true
    }
}