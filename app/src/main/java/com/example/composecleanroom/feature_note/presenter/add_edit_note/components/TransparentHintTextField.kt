package com.example.composecleanroom.feature_note.presenter.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle


@Composable
fun TransparentHintTextField(text:String, hint:String, modifier: Modifier = Modifier, hintVisible:Boolean = true, onValueChange:(String)->Unit, textStyle: TextStyle = TextStyle(), singleLine:Boolean = false,testTag:String = "", onFocusChange:(FocusState)->Unit) {
    Box(modifier) {
        BasicTextField(value = text, onValueChange = onValueChange, textStyle = textStyle,singleLine = singleLine,modifier = Modifier.testTag(testTag)
            .fillMaxWidth()
            .onFocusChanged {
                onFocusChange(it)
            })
        if (hintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }

}