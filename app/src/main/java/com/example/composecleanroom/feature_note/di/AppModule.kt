package com.example.composecleanroom.feature_note.di

import android.app.Application
import androidx.room.Room
import com.example.composecleanroom.feature_note.data.db.Dao
import com.example.composecleanroom.feature_note.data.db.MainDb
import com.example.composecleanroom.feature_note.data.repository.NoteRepositoryImpl
import com.example.composecleanroom.feature_note.domain.repository.NoteRepository
import com.example.composecleanroom.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app:Application):MainDb =
        Room.databaseBuilder(app,MainDb::class.java,"noteCompose.db")
            .build()

    @Provides
    @Singleton
    fun provideRepositoryNote(mainDb: MainDb):NoteRepository =
        NoteRepositoryImpl(mainDb.getDao())

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository):NoteUseCases =
        NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            getNotByIdUseCase = GetNotByIdUseCase(repository)
        )
}