package dev.dacape.example.kotlin.crudroomjetpackcompose.db.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "create")
    var create: Date? = Date(),

    @ColumnInfo(name = "update")
    var update: Date?

)