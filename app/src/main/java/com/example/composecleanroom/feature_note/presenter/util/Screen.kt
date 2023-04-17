package com.example.composecleanroom.feature_note.presenter.util

sealed class Screen(val route:String){
    object NotesScreen:Screen("notes_screen")
    object AddEditNoteScreen:Screen("addeditnote_screen")
}
