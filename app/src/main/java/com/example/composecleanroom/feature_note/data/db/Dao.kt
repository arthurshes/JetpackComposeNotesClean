package com.example.composecleanroom.feature_note.data.db
import androidx.room.*
import androidx.room.Dao
import com.example.composecleanroom.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM noteTable")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM noteTable WHERE id = :id")
    suspend fun getNoteById(id:Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}