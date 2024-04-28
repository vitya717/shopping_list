package com.example.shoppinglist.ui.screens.shop_list_screen

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shoppinglist.ui.screens.elements.BottomNavigationBar
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist.R
import com.example.shoppinglist.data.models.ShopListItem
import com.example.shoppinglist.ui.screens.elements.RailNavigation
import com.example.shoppinglist.ui.screens.elements.ScrollToTopButton
import com.example.shoppinglist.ui.ui_util.isScrollingUp
import com.example.shoppinglist.ui.ui_util.keyboardAsState
import com.example.shoppinglist.util.AppContentType
import com.example.shoppinglist.util.AppNavigationType
import com.example.shoppinglist.util.LoadingIndicator
import kotlinx.coroutines.launch


@Composable
fun ShopListScreen(
    navController: NavHostController,
    navigationType: AppNavigationType,
    contentType: AppContentType,
    modifier: Modifier = Modifier,
    shopListViewModel: ShopListViewModel = hiltViewModel()
) {
    val isKeyboardOpen by keyboardAsState()
    val uiState by shopListViewModel.uaState.collectAsState()
    var showDeleteAllDialog by remember { mutableStateOf(false) }
    var showAddItemDialog by remember { mutableStateOf(false) }

    Row {
        if (navigationType == AppNavigationType.NAVIGATION_RAIL) {
            RailNavigation(navController = navController,
                floatingActionButtonOnClick = { showAddItemDialog = true }

            )
        }
        Scaffold(
            bottomBar = {
                if (navigationType == AppNavigationType.BOTTOM_NAVIGATION) {
                    if(!isKeyboardOpen) {
                        BottomNavigationBar(
                            navController = navController,
                            floatingActionButtonOnClick = { showAddItemDialog = true }
                        )
                    }
                }
            },
            topBar = {
                ShopListTopBar(
                    allItems = uiState.allItemsCount,
                    boughtItems = uiState.boughtItemsCount,
                    onClick = { showDeleteAllDialog = true },
                    enabled = uiState.isNotEmpty
                )
            }
        ) { paddingValues ->
            ShopListContent(
                items = uiState.shopList,
                updateItemIsBought = { item, isBought ->
                    shopListViewModel.updateShopListItemIsBought(item, isBought)
                },
                updateItem = { item, newName, isBought ->
                    shopListViewModel.updateShopListItem(item, newName, isBought)
                },
                deleteItem = { shopListViewModel.deleteShopListItem(it) },
                contentType = contentType,
                modifier = Modifier
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = paddingValues.calculateTopPadding()
                    )
            )

            if (showDeleteAllDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteAllDialog = false },
                    title = { Text(text = stringResource(id = R.string.shop_list_screen_clear_list_dialog_title)) },
                    text = { Text(text = stringResource(id = R.string.shop_list_screen_clear_list_dialog_text)) },
                    confirmButton = {
                        TextButton(onClick = {
                            shopListViewModel.clearShopList()
                            showDeleteAllDialog = false
                        }
                        ) {
                            Text(text = stringResource(id = R.string.shop_list_screen_clear_list_dialog_confirm_button).uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteAllDialog = false }) {
                            Text(text = stringResource(id = R.string.shop_list_screen_clear_list_dialog_dismiss_button).uppercase())
                        }
                    },
                )
            }

            if (showAddItemDialog) {
                Dialog(
                    onDismissRequest = { showAddItemDialog = false }
                ) {
                    var itemName by remember { mutableStateOf("") }
                    Card {
                        Column(
                            modifier = Modifier.padding(7.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(value = itemName, onValueChange = { itemName = it })
                            Button(
                                onClick = {
                                    shopListViewModel.addShopListItem(itemName)
                                    showAddItemDialog = false
                                }
                            ) {
                                Text(stringResource(id = R.string.shop_list_screen_add_item_dialog_add_button))
                            }
                        }
                    }

                }
            }
        }
        LoadingIndicator(uiState.isLoading)
    }
}

@Composable
private fun ShopListContent(
    items: List<ShopListItem>,
    contentType: AppContentType,
    updateItemIsBought: (ShopListItem, Boolean) -> Unit,
    updateItem: (ShopListItem, String, Boolean) -> Unit,
    deleteItem: (Long?) -> Unit,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val columnNumber =
        if (contentType == AppContentType.LIST) { 1 } else { 2 }
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
            items(items) {
                ItemCard(
                    item = it,
                    updateItemIsBought = updateItemIsBought,
                    updateItem = updateItem,
                    deleteItem = deleteItem
                )
            }
        }
        AnimatedVisibility(
            visible = showScrollToTopButton,
            enter = fadeIn(),
            exit = fadeOut()
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemCard(
    item: ShopListItem,
    updateItemIsBought: (ShopListItem, Boolean) -> Unit,
    updateItem: (ShopListItem, String, Boolean) -> Unit,
    deleteItem: (Long?) -> Unit,
) {
    val isKeyboardOpen by keyboardAsState()
    var showItemDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(7.dp)
            .combinedClickable(
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showDeleteDialog = true
                },
                onClick = { showItemDialog = true }
            ),
        colors = when (item.isBought) {
            true -> CardDefaults.cardColors(Color(0xC0C4C4CC))
            false -> CardDefaults.cardColors(Color.Cyan)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name)
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = item.isBought,
                    onCheckedChange = { e -> updateItemIsBought(item, e) }
                )
            }
        }
    }
    if (showItemDialog) {
        Dialog(
            onDismissRequest = { showItemDialog = false }
        ) {
            var itemName by remember { mutableStateOf(item.name) }
            var itemIsBought by remember { mutableStateOf(item.isBought) }
            Card {
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it })
                    RadioButton(
                        selected = itemIsBought,
                        onClick = { itemIsBought = !itemIsBought }
                    )
                    Button(
                        onClick = {
                            updateItem(item, itemName, itemIsBought)
                            showItemDialog = false
                        }
                    ) {
                        Text(text = stringResource(id = R.string.shop_list_screen_item_dialog_save_button))
                    }
                }
            }

        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = stringResource(id = R.string.shop_list_screen_delete_item_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.shop_list_screen_delete_item_dialog_text)) },
            confirmButton = {
                TextButton(onClick = {
                    deleteItem(item.id)
                    showDeleteDialog = false
                }
                ) {
                    Text(text = stringResource(id = R.string.shop_list_screen_delete_item_dialog_confirm_button).uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = stringResource(id = R.string.shop_list_screen_delete_item_dialog_dismiss_button).uppercase())
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListTopBar(
    allItems: String,
    boughtItems: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .shadow(elevation = 5.dp),
        title = {
            Text(text = "$boughtItems / $allItems")
        },
        actions = {
            IconButton(
                onClick = { onClick() },
                enabled = enabled,

                ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.delete_forever_24px),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = when (enabled) {
                        true -> Color.Red
                        false -> Color.Gray
                    },
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ShopListsScreenPreview() {
    ShoppingListTheme {
//        ShopListScreen(navController = rememberNavController())
    }
}
