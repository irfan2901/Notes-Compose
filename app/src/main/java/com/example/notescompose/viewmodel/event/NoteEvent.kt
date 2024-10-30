package com.example.notescompose.viewmodel.event

import com.example.notescompose.model.NotesModel

sealed class NoteEvent {

    data class AddEvent(val notesModel: NotesModel) : NoteEvent()
    data class UpdateEvent(val notesModel: NotesModel) : NoteEvent()
    data class DeleteEvent(val notesModel: NotesModel) : NoteEvent()
    data object LoadNotes : NoteEvent()

}