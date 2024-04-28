package com.example.shoppinglist.domain.notes

import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import javax.inject.Inject

class GetNoteUseCase @Inject constructor (
    private val notesRepository: NotesRepositoryInterface
) {
    suspend fun execute(id: String): Note {
        return notesRepository.getNote(id = id)
    }
}