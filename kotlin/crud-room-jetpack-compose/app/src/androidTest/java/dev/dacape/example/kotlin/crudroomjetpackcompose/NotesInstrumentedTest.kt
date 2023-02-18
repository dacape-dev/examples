package dev.dacape.example.kotlin.crudroomjetpackcompose

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDao
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.NotesDatabase
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class NotesInstrumentedTest {

    private lateinit var notesDao: NotesDao
    private lateinit var db: NotesDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        notesDao = db.notesDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun when_insert_findById() = runBlocking {
        val newNote = Note(id = null, text = "Text", create = null, update = null)
        notesDao.insert(newNote)
        val note = notesDao.findById(1)
        assertEquals(note?.id, 1)
    }
}