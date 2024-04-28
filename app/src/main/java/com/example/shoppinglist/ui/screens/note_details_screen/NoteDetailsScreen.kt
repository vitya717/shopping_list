package com.example.shoppinglist.ui.screens.note_details_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.ui.ui_util.keyboardAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    noteDetailsViewModel: NoteDetailsViewModel = hiltViewModel()
) {
    BackHandler {
        noteDetailsViewModel.saveNoteIfNotEmpty()
        noteDetailsViewModel.deleteNoteIfEmpty()
        navController.popBackStack()
    }

    val uiState by noteDetailsViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(key1 = isKeyboardOpen) {
        if(isKeyboardOpen) scrollState.scrollTo(value = 500)
    }

    Scaffold(
        topBar = {
            NoteDetailsTopBar(
                backButtonOnClick = {
                    noteDetailsViewModel.deleteNoteIfEmpty()
                    navController.popBackStack()
                },
                saveButtonOnClick = {
                    noteDetailsViewModel.saveNoteIfNotEmpty()
                },
                showSaveButton = uiState.isChanged
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextField(
                value = uiState.noteTitle,
                onValueChange = { title -> noteDetailsViewModel.updateTitleField(title) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.note_details_screen_title_text_field_placeholder
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
//                    placeholderColor = Color.Gray
                ),
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            )
            TextField(
                value = uiState.noteDescription,
                onValueChange = { description ->
                    noteDetailsViewModel.updateDescriptionField(
                        description
                    )
                },
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.note_details_screen_description_text_field_placeholder
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
//                    placeholderColor = Color.Gray
                ),
                textStyle = TextStyle(fontSize = 20.sp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsTopBar(
    modifier: Modifier = Modifier,
    backButtonOnClick: () -> Unit = {},
    saveButtonOnClick: () -> Unit = {},
    showSaveButton: Boolean = false
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .shadow(elevation = 5.dp),
        title = { },
        navigationIcon = {
            IconButton(
                onClick = backButtonOnClick,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back_ios_24px),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        actions = {
            AnimatedVisibility(
                visible = showSaveButton,
                enter = fadeIn(),
                exit = fadeOut()
                ) {
                TextButton(
                    onClick = {
                        saveButtonOnClick()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.save_button),
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NoteDetailsTopBarPreview() {
    ShoppingListTheme {
        NoteDetailsTopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailsScreenPreview() {
    ShoppingListTheme {
        NoteDetailsScreen(navController = rememberNavController())
    }
}