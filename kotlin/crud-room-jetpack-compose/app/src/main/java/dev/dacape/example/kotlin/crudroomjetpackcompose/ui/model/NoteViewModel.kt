package dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.repository.NoteRepository

class NoteViewModel(application: Application): ViewModel() {

    private val repository: NoteRepository

    val all = LiveData<List<Note>>()

    init {
        val db = NotesRoomDatabase.getInstance(application)
        val dao = db.noteDao()
        repository = NoteRepository(dao)

        all = repository.all
    }
}