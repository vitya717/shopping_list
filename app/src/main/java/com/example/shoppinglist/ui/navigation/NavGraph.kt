package com.example.shoppinglist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.shoppinglist.ui.screens.note_details_screen.NoteDetailsScreen
import com.example.shoppinglist.ui.screens.notes_screen.NotesListScreen
import com.example.shoppinglist.ui.screens.settings_screen.SettingsScreen
import com.example.shoppinglist.ui.screens.shop_list_screen.ShopListScreen
import com.example.shoppinglist.util.AppContentType
import com.example.shoppinglist.util.AppNavigationType

@Composable
fun NavGraph(
    navController: NavHostController,
    navigationType: AppNavigationType,
    contentType: AppContentType,
    isDarkTheme: Boolean,
    setDarkTheme: () -> Unit
) {
    NavHost(navController = navController, startDestination = BottomNavigationScreens.ShopList.route) {
        composable(route = BottomNavigationScreens.Settings.route) {
            SettingsScreen(
                navController = navController,
                navigationType = navigationType,
                isDarkTheme = isDarkTheme,
                setDarkTheme = setDarkTheme
            )
        }
        composable(route = BottomNavigationScreens.Notes.route) {
            NotesListScreen(navController = navController, navigationType = navigationType, contentType = contentType)
        }
        composable(route = BottomNavigationScreens.ShopList.route) {
            ShopListScreen(navController = navController, navigationType = navigationType, contentType = contentType)
        }
        composable(
            route = Screens.NoteDetails.route.plus(Screens.NoteDetails.objectPath),
            arguments = listOf(navArgument(Screens.NoteDetails.objectName) {
                type = NavType.StringType
            })
        ) {
            NoteDetailsScreen(navController = navController)
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: ""
}