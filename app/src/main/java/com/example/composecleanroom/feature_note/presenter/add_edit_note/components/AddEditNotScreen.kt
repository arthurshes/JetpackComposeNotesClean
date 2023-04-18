package com.example.composecleanroom.feature_note.presenter.add_edit_note.components

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composecleanroom.feature_note.core.utils.TestTags.TEXTFIELD_CONTENT_TAG
import com.example.composecleanroom.feature_note.core.utils.TestTags.TEXTFIELD_TITLE_TAG
import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.presenter.add_edit_note.AddEditevent
import com.example.composecleanroom.feature_note.presenter.add_edit_note.viewModel.AddViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNotScreen(navController: NavController,noteColor:Int,viewModel:AddViewModel = hiltViewModel()) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.event.collectLatest {event->
            when(event){
                is AddViewModel.UIEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
             is AddViewModel.UIEvent.SaveNote ->{
                 navController.navigateUp()
             }

            }
        }
    }
    val noteBackgroundAnimated = remember{
        Animatable(
            Color(if (noteColor != -1)noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(AddEditevent.SaveNote)
        }, backgroundColor = MaterialTheme.colors.primary) {
         Icon(imageVector = Icons.Default.Save, contentDescription = "Сохранить")
        }
    }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimated.value)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Note.noteColors.forEach {color->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp, color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimated.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditevent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(text = titleState.text, hint = titleState.hint, onValueChange = {
                viewModel.onEvent(AddEditevent.EnteredTitle(it))
            }, onFocusChange = {
                viewModel.onEvent(AddEditevent.FocusChangeTitle(it))
            }, hintVisible = titleState.isHintVisible,
            singleLine = true, textStyle = MaterialTheme.typography.h5,
            testTag = TEXTFIELD_TITLE_TAG)

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(text = contentState.text, hint = contentState.hint, onValueChange = {
                viewModel.onEvent(AddEditevent.EnteredContent(it))
            }, onFocusChange = {
                viewModel.onEvent(AddEditevent.FocusChangeContent(it))
            }, hintVisible = contentState.isHintVisible,
                singleLine = false, textStyle = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxHeight(),
                testTag = TEXTFIELD_CONTENT_TAG
            )
        }
    }
}