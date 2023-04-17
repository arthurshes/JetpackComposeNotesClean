package com.example.composecleanroom.feature_note.presenter.notes.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.use_case.NoteUseCases
import com.example.composecleanroom.feature_note.domain.utils.NoteOrder
import com.example.composecleanroom.feature_note.domain.utils.OrderType
import com.example.composecleanroom.feature_note.presenter.notes.NoteState
import com.example.composecleanroom.feature_note.presenter.notes.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases):ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeleteNote: Note? = null
    private var getNotesJob: Job?=null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class && state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
              viewModelScope.launch {
                  noteUseCases.deleteNoteUseCase(event.note)
                  recentlyDeleteNote = event.note
              }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
       getNotesJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach {
                notes ->
                _state.value = state.value.copy(notes,
                noteOrder)
            }
            .launchIn(viewModelScope)
    }
}