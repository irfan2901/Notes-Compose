package com.example.notescompose.repository

import com.example.notescompose.model.NotesDao
import com.example.notescompose.model.NotesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRepository(private val dao: NotesDao) {

    suspend fun addNote(notesModel: NotesModel) = withContext(Dispatchers.IO) {
        dao.addNote(notesModel)
    }

    suspend fun updateNote(notesModel: NotesModel) = withContext(Dispatchers.IO) {
        dao.updateNote(notesModel)
    }

    suspend fun deleteNote(notesModel: NotesModel) = withContext(Dispatchers.IO) {
        dao.deleteNote(notesModel)
    }

    fun showAllNotes(): Flow<List<NotesModel>> {
        return dao.showAllNotes()
    }

}