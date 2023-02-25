package dev.dacape.example.kotlin.crudroomjetpackcompose.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note

@Dao
interface NotesDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM notes")
    fun all(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun findById(id: Int): Note

}