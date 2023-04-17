package com.example.composecleanroom.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composecleanroom.ui.theme.*

@Entity("noteTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val title:String,
    val content:String,
    val timestamp:Long,
    val color:Int,
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
class InvalidNoteException(message:String):Exception(message)