package com.example.shoppinglist.data.repositories

import com.example.shoppinglist.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepositoryInterface {
    suspend fun getNotes() : List<Note>
    fun getNotesStream() : Flow<List<Note>>
    suspend fun getNote(id: String) : Note
    fun getNoteStream(id: String) : Flow<Note>
    suspend fun getSummaryNotes() : List<Note>
    fun getSummaryNotesStream() : Flow<List<Note>>
    suspend fun addNote(): String?
    suspend fun updateNote(id: String, updatedTitle: String, updatedDescription: String)
    suspend fun updateNoteTitle(id: String, updatedTitle: String)
    suspend fun updateNoteDescription(id: String, updatedDescription: String)
    suspend fun deleteNoteById(id: String)
}