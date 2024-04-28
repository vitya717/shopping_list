package com.example.shoppinglist.domain.notes

import com.example.shoppinglist.R
import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.util.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class GetSummaryNotesStreamUseCase @Inject constructor(
    private val notesRepository: NotesRepositoryInterface
) {
    fun execute(): Flow<Async<List<Note>>>  {
        return notesRepository.getSummaryNotesStream()
            .map {
                val list = it.sortedByDescending { e -> e.lastUpdatedDateTime}
                Async.Success(list)
            }
            .catch<Async<List<Note>>> { emit(Async.Error(R.string.loading_notes_error)) }
    }
}