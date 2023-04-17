package com.example.composecleanroom.feature_note.domain.use_case

import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.repository.NoteRepository

class GetNotByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}