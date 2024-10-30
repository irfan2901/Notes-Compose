package com.example.notescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescompose.model.NotesModel
import com.example.notescompose.repository.NotesRepository
import com.example.notescompose.viewmodel.event.NoteEvent
import com.example.notescompose.viewmodel.state.NoteState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {

    private var _state = MutableStateFlow(
        NoteState(
            notes = flow { emit(emptyList()) },
            isLoading = false,
            error = null
        )
    )
    val state: Flow<NoteState> = _state.asStateFlow()

    init {
        onEvent(NoteEvent.LoadNotes)
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.AddEvent -> addNote(event.notesModel)
            is NoteEvent.UpdateEvent -> updateNote(event.notesModel)
            is NoteEvent.DeleteEvent -> deleteNote(event.notesModel)
            is NoteEvent.LoadNotes -> loadNotes()
        }
    }

    private fun addNote(notesModel: NotesModel) {
        viewModelScope.launch {
            try {
                repository.addNote(notesModel)
                loadNotes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun updateNote(notesModel: NotesModel) {
        viewModelScope.launch {
            try {
                repository.updateNote(notesModel)
                loadNotes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun deleteNote(notesModel: NotesModel) {
        viewModelScope.launch {
            try {
                repository.deleteNote(notesModel)
                loadNotes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val notes = repository.showAllNotes()
                _state.value = _state.value.copy(notes = notes, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

}