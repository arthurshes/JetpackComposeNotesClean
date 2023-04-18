package com.example.composecleanroom.feature_note.domain.use_case

import com.example.composecleanroom.feature_note.data.FakeNotesRepository
import com.example.composecleanroom.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class InsertNoteUseCaseTest{
    private lateinit var insertNoteUseCase: InsertNoteUseCase
    private lateinit var fakeNotesRepository: FakeNotesRepository

    @Before
    fun setUp(){
        fakeNotesRepository = FakeNotesRepository()
        insertNoteUseCase = InsertNoteUseCase(fakeNotesRepository)
    }

    @Test
    fun insertTitleWithReadAndDelete_returnSuccess() = runTest{
        val note = Note(title = "gpgktk", content = "gokyokoykhoykh", color = 12345, timestamp = 123455, id = 1)
        fakeNotesRepository.insertNote(note)
        val result = fakeNotesRepository.getNotes().first()
        assertThat(result[0]).isEqualTo(note)
        fakeNotesRepository.deleteNote(note)
    }
}