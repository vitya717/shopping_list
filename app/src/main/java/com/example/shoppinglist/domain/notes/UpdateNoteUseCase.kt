package com.example.shoppinglist.domain.notes

import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor (
    private val notesRepository: NotesRepositoryInterface
) {
    suspend fun execute(id: String, updatedTitle: String, updatedDescription: String) {
        notesRepository.updateNote(
            id = id,
            updatedTitle = updatedTitle,
            updatedDescription = updatedDescription
        )
    }
}