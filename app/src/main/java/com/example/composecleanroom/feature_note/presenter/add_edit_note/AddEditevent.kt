package com.example.composecleanroom.feature_note.presenter.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditevent {
    data class EnteredTitle(val value:String):AddEditevent()
    data class EnteredContent(val value:String):AddEditevent()
    data class FocusChangeTitle(val focusState: FocusState):AddEditevent()
    data class FocusChangeContent(val focusState: FocusState):AddEditevent()
    data class ChangeColor(val color:Int):AddEditevent()
    object SaveNote:AddEditevent()
}
