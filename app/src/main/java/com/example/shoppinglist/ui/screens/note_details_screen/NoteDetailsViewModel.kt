package com.example.shoppinglist.ui.screens.note_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.domain.notes.DeleteNoteUseCase
import com.example.shoppinglist.domain.notes.GetNoteUseCase
import com.example.shoppinglist.domain.notes.UpdateNoteUseCase
import com.example.shoppinglist.ui.screens.shop_list_screen.ShopListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

data class NoteDetailsUiState(
    val noteId: String = "",
    val noteTitle: String = "",
    val noteDescription: String = "",
    val isEmpty: Boolean = true,
    val isNotEmpty: Boolean = false,
    val isChanged: Boolean = false
)

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _note = MutableStateFlow(
        Note(
            lastUpdatedDateTime = Clock.System.now().toLocalDateTime(
                timeZone = TimeZone.currentSystemDefault()
            )
        )
    )
    private val _isEmpty = MutableStateFlow(true)
    private val _isNotEmpty = MutableStateFlow(false)
    private val _isChanged = MutableStateFlow(false)


    init {
        val noteId: String = savedStateHandle["noteId"]!!
        viewModelScope.launch {
            updateNoteFields(getNoteUseCase.execute(noteId))
        }
    }

    val uiState = combine(_note, _isEmpty, _isChanged) { note, isEmpty, isChanged ->
        NoteDetailsUiState(
            noteId = note.id,
            noteTitle = note.title,
            noteDescription = note.description,
            isEmpty = isEmpty,
            isNotEmpty = !isEmpty,
            isChanged = isChanged
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NoteDetailsUiState()
        )

    private fun updateNoteFields(note: Note) {
        _note.update { currentState ->
            currentState.copy(
                id = note.id,
                title = note.title,
                description = note.description,
            )
        }
        _isEmpty.value = isEmpty()
        _isNotEmpty.value = !_isEmpty.value
    }

    fun updateTitleField(updatedTitle: String) {
        _note.update { currentState ->
            currentState.copy(
                title = updatedTitle
            )
        }
        _isEmpty.value = isEmpty()
        _isNotEmpty.value = !_isEmpty.value
        _isChanged.value = true
    }

    fun updateDescriptionField(updatedDescription: String) {
        _note.update { currentState ->
            currentState.copy(
                description = updatedDescription
            )
        }
        _isEmpty.value = isEmpty()
        _isNotEmpty.value = !_isEmpty.value
        _isChanged.value = true
    }

    fun saveNoteIfNotEmpty(callback: () -> Unit = {}) {
        if (_isNotEmpty.value && _isChanged.value) {
            viewModelScope.launch {
                updateNoteUseCase.execute(
                    id = _note.value.id,
                    updatedTitle = _note.value.title,
                    updatedDescription = _note.value.description
                )
                callback()
            }
        }
        _isChanged.value = false
    }

    fun deleteNoteIfEmpty() {
        if(_isEmpty.value) deleteNote()
    }


    private fun deleteNote() {
        viewModelScope.launch {
            deleteNoteUseCase.execute(id = _note.value.id)
        }
    }

    private fun isEmpty() =
        _note.value.title.isEmpty()
                && _note.value.description.isEmpty()

    private fun saveNote() {
        viewModelScope.launch {
            updateNoteUseCase.execute(
                id = _note.value.id,
                updatedTitle = _note.value.title,
                updatedDescription = _note.value.description
            )
        }
    }
}