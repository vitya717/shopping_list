@file:OptIn(ExperimentalFoundationApi::class)

package com.example.shoppinglist.ui.screens.notes_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shoppinglist.R
import com.example.shoppinglist.data.models.Note
import com.example.shoppinglist.ui.navigation.Screens
import com.example.shoppinglist.ui.screens.elements.BottomNavigationBar
import com.example.shoppinglist.ui.screens.elements.RailNavigation
import com.example.shoppinglist.ui.screens.elements.ScrollToTopButton
import com.example.shoppinglist.ui.ui_util.isScrollingUp
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.util.AppContentType
import com.example.shoppinglist.util.AppNavigationType
import com.example.shoppinglist.util.LoadingIndicator
import kotlinx.coroutines.launch

@Composable
fun NotesListScreen(
    navController: NavHostController,
    navigationType: AppNavigationType,
    contentType: AppContentType,
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val uiState by notesViewModel.uiState.collectAsState()

    Row {
        if (navigationType == AppNavigationType.NAVIGATION_RAIL) {
            RailNavigation(navController = navController,
                floatingActionButtonOnClick = {
                    notesViewModel.addNewNote { id ->
                        navController.navigate(Screens.NoteDetails.route + "/" + id)
                    }
                }
            )
        }
        Scaffold(
            bottomBar = {
                if (navigationType == AppNavigationType.BOTTOM_NAVIGATION) {
                    BottomNavigationBar(
                        navController = navController,
                        floatingActionButtonOnClick = {
                            notesViewModel.addNewNote { id ->
                                navController.navigate(Screens.NoteDetails.route + "/" + id)
                            }
                        }
                    )
                }
            },
            modifier = modifier
        ) {
            Box {
                NotesScreenContent(
                    notes = uiState.notesList,
                    contentType = contentType,
                    noteDetails = { id -> navController.navigate(Screens.NoteDetails.route + "/${id}") },
                    deleteNote = { id -> notesViewModel.deleteNote(id) },
                    modifier = Modifier.padding(it)
                )
                LoadingIndicator(uiState.isLoading)
            }
        }
    }
}

@Composable
private fun NotesScreenContent(
    notes: List<Note>,
    contentType: AppContentType,
    deleteNote: (String) -> Unit,
    noteDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val columnNumber =
        if (contentType == AppContentType.LIST) {
            1
        } else {
            2
        }
    val state = rememberLazyGridState()
    val firstVisibleItemIndexMoreThanZero by remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 0
        }
    }
    val showScrollToTopButton = state.isScrollingUp() && firstVisibleItemIndexMoreThanZero

    Box(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnNumber),
            state = state
        ) {
            items(notes) {
                NoteCard(
                    note = it,
                    onClick = noteDetails,
                    onLongClick = deleteNote,
                )
            }
        }
        AnimatedVisibility(
            visible = showScrollToTopButton,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    state.animateScrollToItem(index = 0)
                }
            }
            )
        }
    }
}

@Composable
private fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
    onLongClick: (String) -> Unit = {},
    cardColor: Color = Color.Green,
) {

    val haptic = LocalHapticFeedback.current
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(note.id) },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showDialog = true
                }
            )
            .padding(top = 2.dp, bottom = 2.dp, start = 1.dp, end = 1.dp),
        colors = CardDefaults.cardColors(cardColor),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, end = 13.dp),
            ) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, end = 13.dp),
            ) {
                Text(
                    text = note.description, maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(id = R.string.notes_screen_delete_note_dialog_title)) },
            text = { Text(stringResource(id = R.string.notes_screen_delete_note_dialog_text)) },
            confirmButton = {
                TextButton(onClick = {
                    onLongClick(note.id)
                    showDialog = false
                }
                ) {
                    Text(stringResource(id = R.string.notes_screen_delete_note_dialog_confirm_button).uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.notes_screen_delete_note_dialog_dismiss_button).uppercase())
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    ShoppingListTheme {
//        NotesListScreen(navController = rememberNavController())
    }
}