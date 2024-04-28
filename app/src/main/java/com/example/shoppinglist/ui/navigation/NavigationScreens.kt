package com.example.shoppinglist.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.shoppinglist.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Settings: BottomNavigationScreens(
        route = "settings",
        label = R.string.settings_bottom_navigation_bar_item_label,
        icon = R.drawable.settings_24px)
    object Notes: BottomNavigationScreens(
        route = "notes",
        label = R.string.notes_bottom_navigation_bar_item_label,
        icon = R.drawable.notes_24px)
    object ShopList: BottomNavigationScreens(
        route = "shop_lists",
        label = R.string.shop_list_bottom_navigation_bar_item_label,
        icon = R.drawable.list_24px)
    object AddNewItem: BottomNavigationScreens(
        route = "add_new_item",
        label = R.string.add_new_item_bottom_navigation_bar_item_label,
        icon = R.drawable.add_24px)

    companion object {
        val navigationItems = listOf(
        Settings,
        ShopList,
        Notes,
        )
    }
}

sealed class Screens(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    object NoteDetails: Screens(route = "note_details", objectName = "noteId", objectPath = "/{noteId}")
}
