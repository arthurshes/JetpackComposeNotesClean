package com.example.composecleanroom.feature_note.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composecleanroom.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class MainDb:RoomDatabase() {
    abstract fun getDao():Dao
}