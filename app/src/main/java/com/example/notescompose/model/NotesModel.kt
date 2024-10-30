package com.example.notescompose.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "notes_table")
data class NotesModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "note_title")
    val title: String? = null,

    @ColumnInfo(name = "note_description")
    val description: String? = null,

    @ColumnInfo(name = "note_date")
    val time: String? = null

) : Parcelable
