package com.example.composecleanroom.feature_note.presenter.notes

import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.utils.NoteOrder
import com.example.composecleanroom.feature_note.domain.utils.OrderType

data class NoteState(
    val notes:List<Note> = emptyList(),
    val noteOrder:NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean  = false
)
