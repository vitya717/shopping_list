package com.example.shoppinglist.domain.notes

import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import javax.inject.Inject

class AddNewNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepositoryInterface
) {
    suspend fun execute(): String {
        return notesRepository.addNote()!!
    }
}
