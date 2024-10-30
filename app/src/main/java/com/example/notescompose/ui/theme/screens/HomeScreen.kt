package com.example.notescompose.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notescompose.routes.Routes
import com.example.notescompose.ui.theme.components.ErrorScreen
import com.example.notescompose.ui.theme.components.LoadingScreen
import com.example.notescompose.ui.theme.components.NotesHome
import com.example.notescompose.viewmodel.NoteViewModel
import com.example.notescompose.viewmodel.state.NoteState
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewmodel: NoteViewModel = viewModel(),
    navController: NavHostController
) {
    val state = viewmodel.state.collectAsState(
        initial = NoteState(
            notes = flow { emit(emptyList()) },
            isLoading = true
        )
    )

    Box(modifier.fillMaxSize()) {
        when {
            state.value.isLoading -> LoadingScreen(modifier)
            state.value.error != null -> ErrorScreen(modifier)
            else -> {
                val notesList = state.value.notes.collectAsState(initial = emptyList()).value
                LazyColumn {
                    items(notesList.chunked(2)) { notes ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            notes.forEach { note ->
                                NotesHome(note = note, navController, modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate(Routes.AddNote) },
            shape = FloatingActionButtonDefaults.extendedFabShape,
            containerColor = Color.Blue,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(30.dp)
        ) {
            Icon(painter = rememberVectorPainter(Icons.Default.Add), contentDescription = "note")
        }
    }
}
