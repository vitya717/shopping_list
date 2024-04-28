package com.example.shoppinglist.data.repositories

import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.data.models.toExternal
import com.example.shoppinglist.data.sources.local.database.NoteDao
import com.example.shoppinglist.data.sources.local.entities.NoteEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID


class NotesRepository(private val noteDao: NoteDao) : NotesRepositoryInterface {

    override fun getNotesStream(): Flow<List<Note>> {
        return noteDao.observeNotes().map { notes ->
            withContext(IO) {
                notes.toExternal()
            }
        }
    }

    override suspend fun getNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override fun getNoteStream(id: String): Flow<Note> {
        return noteDao.observeNoteById(id).map { note ->
            withContext(IO) {
                note.toExternal()
            }
        }
    }

    override suspend fun getNote(id: String): Note {
        var note: NoteEntity
        withContext(IO) {
            note = noteDao.getNoteById(id)
        }
        return note.toExternal()
    }

    override fun getSummaryNotesStream(): Flow<List<Note>> {
        return noteDao.observeSummaryNotes().map { notes ->
            withContext(IO) {
                notes.toExternal()
            }
        }
    }

    override suspend fun getSummaryNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(): String {
        var taskId: String
        withContext(IO) {
            taskId = UUID.randomUUID().toString()
            noteDao.addNote(
                noteEntity = NoteEntity(
                    id = taskId,
                    title = "",
                    description = "",
                    lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
                        timeZone = TimeZone.currentSystemDefault()
                    )
                )
            )
        }
        return taskId
    }

    override suspend fun updateNote(
        id: String,
        updatedTitle: String,
        updatedDescription: String
    ) {
        withContext(IO) {
            noteDao.updateNote(
                NoteEntity(
                id = id,
                title = updatedTitle,
                description = updatedDescription,
                lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
                    timeZone = TimeZone.currentSystemDefault()
                )
            )
            )
//            noteDao.updateNote(
//                id = id,
//                updatedTitle = updatedTitle,
//                updatedDescription = updatedDescription,
//                lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
//                    timeZone = TimeZone.currentSystemDefault()
//                )
//            )
        }
    }

    override suspend fun updateNoteTitle(
        id: String,
        updatedTitle: String,
    ) {
        withContext(IO) {
            noteDao.updateNoteTitle(
                id = id,
                updatedTitle = updatedTitle,
                lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
                    timeZone = TimeZone.currentSystemDefault()
                )
            )
        }
    }

    override suspend fun updateNoteDescription(
        id: String,
        updatedDescription: String,
    ) {
        withContext(IO) {
            noteDao.updateNoteDescription(
                id = id,
                updatedDescription = updatedDescription,
                lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
                    timeZone = TimeZone.currentSystemDefault()
                )
            )
        }
    }

    override suspend fun deleteNoteById(id: String) {
        withContext(IO) {
            noteDao.deleteNoteById(id = id)
        }
    }
}