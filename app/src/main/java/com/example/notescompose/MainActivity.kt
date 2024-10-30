package com.example.notescompose

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notescompose.database.NotesDatabase
import com.example.notescompose.model.NotesModel
import com.example.notescompose.repository.NotesRepository
import com.example.notescompose.routes.Routes
import com.example.notescompose.ui.theme.NotesComposeTheme
import com.example.notescompose.ui.theme.screens.HomeScreen
import com.example.notescompose.ui.theme.screens.ShowNotesScreen
import com.example.notescompose.viewmodel.NoteViewModel
import com.example.notescompose.viewmodel.NotesViewModelFactory
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NotesDatabase.getDatabase(this)
        val repository = NotesRepository(database.notesDao())
        val viewmodel =
            ViewModelProvider(this, NotesViewModelFactory(repository))[NoteViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            NotesComposeTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = Routes.Home) {
                        composable(Routes.Home) {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewmodel = viewmodel,
                                navController
                            )
                        }
                        composable(Routes.AddNote) {
                            ShowNotesScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                isNewNote = true,
                                note = null,
                                viewModel = viewmodel
                            )
                        }
                        composable(Routes.UpdateNote) { backstackEntry ->
                            val noteBase64 = backstackEntry.arguments?.getString("note")
                            val noteJson = noteBase64?.let {
                                String(Base64.decode(it, Base64.DEFAULT))
                            }
                            val note = noteJson?.let { Json.decodeFromString<NotesModel>(it) }
                            ShowNotesScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                isNewNote = false,
                                note,
                                viewmodel
                            )
                        }
                    }
                }
            }
        }
    }
}