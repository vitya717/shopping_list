package com.example.shoppinglist.domain.notes

import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepositoryInterface
) {
    suspend fun execute(id: String) {
        notesRepository.deleteNoteById(id)
    }
}