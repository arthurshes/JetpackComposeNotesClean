package com.example.composecleanroom.feature_note.data.repository

import com.example.composecleanroom.feature_note.data.db.Dao
import com.example.composecleanroom.feature_note.domain.model.Note
import com.example.composecleanroom.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao: Dao):NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
       return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}