package com.example.shoppinglist.ui.screens.notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.R
import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.domain.notes.AddNewNoteUseCase
import com.example.shoppinglist.domain.notes.DeleteNoteUseCase
import com.example.shoppinglist.domain.notes.GetSummaryNotesStreamUseCase
import com.example.shoppinglist.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesUiState(
    val notesList: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val showUserMessage: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNewNoteUseCase: AddNewNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    getSummaryNotesUseCase: GetSummaryNotesStreamUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow<Int?>(1)
    private val _showUserMessage = MutableStateFlow(false)
    private val _notes = getSummaryNotesUseCase.execute()


    val uiState: StateFlow<NotesUiState> =
        combine(_isLoading, _userMessage, _showUserMessage, _notes) { isLoading, userMessage, showUserMessage, notes ->

            when(notes) {
                Async.Loading -> {
                    NotesUiState(isLoading = true)
                }
                is Async.Error -> {
                    NotesUiState(
                        isLoading = isLoading,
                        userMessage = notes.errorMessage,
                        showUserMessage = true
                        )
                }
                is Async.Success -> {
                    NotesUiState(
                        notesList = notes.data,
                        isLoading = isLoading,
                        showUserMessage = showUserMessage,
                        userMessage = userMessage
                        )
                }
            }
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                NotesUiState(isLoading = true)
            )


    fun deleteNote(id: String) {
        viewModelScope.launch {
            try {
                _isLoading.emit(true)
                deleteNoteUseCase.execute(id)
            } catch (e: Exception) {
                _userMessage.value = R.string.creating_new_note_error

            } finally {
                _isLoading.emit(false)
            }
        }
    }

    fun addNewNote(callback: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.emit(true)
                val id = addNewNoteUseCase.execute()
                callback(id)
            } catch (e: Exception) {
                /*
                TODO
                 */
            } finally {
                _isLoading.emit(false)
            }
        }
    }

    fun userMessageShown() {
        _userMessage.value = null
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }

}
