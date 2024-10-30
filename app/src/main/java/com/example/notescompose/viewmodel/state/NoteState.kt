package com.example.notescompose.viewmodel.state

import com.example.notescompose.model.NotesModel
import kotlinx.coroutines.flow.Flow

data class NoteState(

    val notes: Flow<List<NotesModel>>,
    val isLoading: Boolean = false,
    val error: String? = null

)
