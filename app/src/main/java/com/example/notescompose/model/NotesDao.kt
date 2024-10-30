package com.example.notescompose.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notesModel: NotesModel)

    @Update
    suspend fun updateNote(notesModel: NotesModel)

    @Delete
    suspend fun deleteNote(notesModel: NotesModel)

    @Query("SELECT * FROM notes_table")
    fun showAllNotes(): Flow<List<NotesModel>>

}