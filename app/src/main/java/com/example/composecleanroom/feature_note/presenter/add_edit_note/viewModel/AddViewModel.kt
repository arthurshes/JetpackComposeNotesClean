package com.example.composecleanroom.feature_note.presenter.add_edit_note.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecleanroom.feature_note.domain.model.InvalidNoteException
import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.use_case.NoteUseCases
import com.example.composecleanroom.feature_note.presenter.add_edit_note.AddEditevent
import com.example.composecleanroom.feature_note.presenter.add_edit_note.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val noteUseCases: NoteUseCases,savedStateHandle: SavedStateHandle):ViewModel() {
    private val _noteTitle = mutableStateOf(TextFieldState(
        hint = "Введите заголовок..."
    ))
    val noteTitle: State<TextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(TextFieldState(
        hint = "Введите описание..."
    ))
    val noteContent: State<TextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor:State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val event = _eventFlow.asSharedFlow()

    private var currentid:Int?=null
    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNotByIdUseCase(noteId)?.also {
                        currentid = it.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                        _noteColor.value = it.color
                    }
                }
            }
        }
    }
    fun onEvent(event:AddEditevent){
        when(event){
            is AddEditevent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditevent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditevent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditevent.FocusChangeContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditevent.FocusChangeTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditevent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNoteUseCase(
                            Note(title = noteTitle.value.text,
                            content = noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                                id = currentid
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    }catch (e:InvalidNoteException){
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                message = e.message ?:
                                "Hеизвестная ошибка"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent{
        data class ShowSnackBar(
            val message:String
        ):UIEvent()
        object SaveNote:UIEvent()
    }
}