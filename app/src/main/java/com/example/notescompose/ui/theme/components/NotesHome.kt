package com.example.notescompose.ui.theme.components

import android.util.Base64
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notescompose.model.NotesModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun NotesHome(note: NotesModel, navController: NavHostController, modifier: Modifier) {
    Card(
        modifier
            .padding(8.dp)
            .clickable(onClick = {
                val noteJson = Json.encodeToString(note)
                val encodedNoteJson = Base64.encodeToString(noteJson.toByteArray(), Base64.DEFAULT)
                navController.navigate("updateNote/$encodedNoteJson")
            }),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title ?: "Untitled",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description ?: "No description",
                style = TextStyle(color = Color.Black),
                modifier = Modifier.align(Alignment.Start),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.time ?: "Time not available",
                modifier = Modifier.align(Alignment.End),
                style = TextStyle(color = Color.Black)
            )
        }
    }
}