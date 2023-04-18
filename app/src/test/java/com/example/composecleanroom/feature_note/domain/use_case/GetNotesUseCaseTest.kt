package com.example.composecleanroom.feature_note.domain.use_case


import android.provider.ContactsContract.CommonDataKinds.Note
import com.example.composecleanroom.feature_note.data.FakeNotesRepository
import com.example.composecleanroom.feature_note.domain.utils.NoteOrder
import com.example.composecleanroom.feature_note.domain.utils.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest{

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var FakeRepo:FakeNotesRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        FakeRepo = FakeNotesRepository()
        getNotesUseCase = GetNotesUseCase(FakeRepo)
        val notesInsert = mutableListOf<com.example.composecleanroom.feature_note.domain.model.Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesInsert.add(
              com.example.composecleanroom.feature_note.domain.model.Note(
                  title = c.toString(),
                  content = c.toString(),
                  timestamp = index.toLong(),
                  color = index
              )
            )
        }
        notesInsert.shuffle()
        runTest {
            notesInsert.forEach {
                FakeRepo.insertNote(it)
            }
        }
    }


    @Test
    fun `Order notes by title ascending,correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }

    }

    @Test
    fun `Order notes by title descending correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }

    }

    @Test
    fun `Order notes by date ascending,correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }

    }

    @Test
    fun `Order notes by date descending,correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }

    }

    @Test
    fun `Order notes by color ascending,correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }

    }

    @Test
    fun `Order notes by color descending,correct order`() = runTest {
        val notes = getNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()
        for ( i in 0..notes.size - 2 ) {
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }

    }

}