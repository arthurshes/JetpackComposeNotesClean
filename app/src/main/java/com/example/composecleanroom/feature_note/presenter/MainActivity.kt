package com.example.composecleanroom.feature_note.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composecleanroom.feature_note.presenter.add_edit_note.components.AddEditNotScreen
import com.example.composecleanroom.feature_note.presenter.notes.components.NotesScreen
import com.example.composecleanroom.feature_note.presenter.util.Screen
import com.example.composecleanroom.ui.theme.ComposeCleanRoomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCleanRoomTheme {
                // A surface container using the 'background' color from the theme
             Surface(color = MaterialTheme.colors.background) {
                 val navController = rememberNavController()
                 NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                     composable(route = Screen.NotesScreen.route){
                         NotesScreen(navController = navController)
                     }
                     composable(route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}", arguments = listOf(
                         navArgument(
                             "noteId"
                         ){
                             type = NavType.IntType
                             defaultValue = -1
                         },
                         navArgument(
                             "noteColor"
                         ){
                             type = NavType.IntType
                             defaultValue = -1
                         }
                     )){
                         val color = it.arguments?.getInt("noteColor") ?: -1
                         AddEditNotScreen(navController = navController, noteColor = color)
                     }
                 }
             }
            }
        }
    }
}

