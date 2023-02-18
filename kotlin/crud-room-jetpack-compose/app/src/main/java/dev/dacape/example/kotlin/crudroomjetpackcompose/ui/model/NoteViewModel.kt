package dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDatabase
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.repository.NotesRepository

class NoteViewModel(application: Application): ViewModel() {

    private val repository: NotesRepository

    val all: LiveData<List<Note>>

    init {
        val db = NotesDatabase.getInstance(application)
        val dao = db.notesDao()
        repository = NotesRepository(dao)

        all = repository.all()
    }
}