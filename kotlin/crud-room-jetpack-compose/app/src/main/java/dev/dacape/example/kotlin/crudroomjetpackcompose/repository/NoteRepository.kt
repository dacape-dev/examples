package dev.dacape.example.kotlin.crudroomjetpackcompose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NoteDao
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(private val noteDao: NoteDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insert(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    fun update(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    fun all(): LiveData<List<Note>> {
        return noteDao.all()
    }

    fun delete(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.delete(id)
        }
    }
}