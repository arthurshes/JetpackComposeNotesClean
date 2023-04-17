package com.example.composecleanroom.feature_note.domain.use_case

import com.example.composecleanroom.feature_note.domain.model.InvalidNoteException
import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.repository.NoteRepository

class InsertNoteUseCase(private val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("Заголовок заметки не может быть пустым")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("Содержимое заметки не может быть пустым")
        }
        repository.insertNote(note)
    }
}