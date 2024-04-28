package com.example.shoppinglist.data.sources.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.data.sources.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun observeNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note")
    fun getNotes(): List<NoteEntity>

    @Query("SELECT id, SUBSTR(title, 1, 150) AS title, SUBSTR(description, 1, 150) AS description, lastUpdatedDateTime FROM note;")
    fun observeSummaryNotes(): Flow<List<NoteEntity>>

    @Query("SELECT id, SUBSTR(title, 1, 150) AS title, SUBSTR(description, 1, 150) AS description, lastUpdatedDateTime FROM note;")
    fun getSummaryNotes(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE id = :id")
    fun observeNoteById(id: String): Flow<NoteEntity>

    @Query("SELECT * FROM note WHERE id=(:id)")
    fun getNoteById(id: String): NoteEntity

    @Insert
    fun addNote(noteEntity: NoteEntity)

//    @Query("UPDATE note " + "SET " +
//            "title = :updatedTitle, " +
//            "description = :updatedDescription, " +
//            "lastUpdatedDateTime = :lastUpdatedDateTime " +
//            "WHERE id IS :id")
//    fun updateNote(id: String, updatedTitle: String, updatedDescription: String, lastUpdatedDateTime: LocalDateTime)
    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Query("UPDATE note " + "SET " +
            "title = :updatedTitle, " +
            "lastUpdatedDateTime = :lastUpdatedDateTime " +
            "WHERE id IS :id")
    fun updateNoteTitle(id: String, updatedTitle: String, lastUpdatedDateTime: LocalDateTime)

    @Query("UPDATE note " + "SET " +
            "description = :updatedDescription, " +
            "lastUpdatedDateTime = :lastUpdatedDateTime " +
            "WHERE id IS :id")
    fun updateNoteDescription(id: String, updatedDescription: String, lastUpdatedDateTime: LocalDateTime)

    @Query("DELETE FROM note WHERE id IS :id")
    fun deleteNoteById(id: String)

}
