package com.example.notescompose.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notescompose.model.NotesModel
import com.example.notescompose.viewmodel.NoteViewModel
import com.example.notescompose.viewmodel.event.NoteEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun ShowNotesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isNewNote: Boolean,
    note: NotesModel?,
    viewModel: NoteViewModel = viewModel()
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }

    val currentDate: LocalDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)

    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            placeholder = { Text(text = "Title", color = Color.Gray) },
            label = { Text("Title") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier.height(10.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            placeholder = { Text(text = "Description", color = Color.Gray) },
            label = { Text("Description") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier.height(10.dp))
        Button(onClick = {
            if (isNewNote) {
                addNote(title, description, formattedDate, viewModel, navController)
            } else {
                note?.id?.let { id ->
                    updateNote(
                        id,
                        title,
                        description,
                        formattedDate,
                        viewModel,
                        navController
                    )
                }
            }
        }) {
            Text(text = if (isNewNote) "Add Note" else "Update Note")
        }
        AnimatedVisibility(visible = !isNewNote) {
            Button(
                onClick = {
                    note?.let {
                        viewModel.onEvent(NoteEvent.DeleteEvent(it))
                        navController.popBackStack()
                    }
                },
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Delete")
            }
        }
    }
}

fun addNote(
    title: String,
    description: String,
    formattedDate: String,
    viewModel: NoteViewModel,
    navController: NavHostController
) {
    val note = NotesModel(
        0,
        title = title,
        description = description,
        time = formattedDate
    )
    viewModel.onEvent(NoteEvent.AddEvent(note))
    navController.popBackStack()
}

fun updateNote(
    id: Int,
    title: String,
    description: String,
    formattedDate: String,
    viewModel: NoteViewModel,
    navController: NavHostController
) {
    val note = NotesModel(
        id = id,
        title = title,
        description = description,
        time = formattedDate
    )
    viewModel.onEvent(NoteEvent.UpdateEvent(note))
    navController.popBackStack()
}
