package com.example.shoppinglist.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.navigation.NavGraph
import com.example.shoppinglist.util.AppContentType
import com.example.shoppinglist.util.AppNavigationType

@Composable
fun ShoppingListApp(
    windowSize: WindowWidthSizeClass,
    isDarkTheme: Boolean,
    setDarkTheme: () -> Unit
) {
    val navController = rememberNavController()
    val navigationType: AppNavigationType
    val contentType: AppContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
            contentType = AppContentType.LIST
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = AppNavigationType.NAVIGATION_RAIL
            contentType = AppContentType.GRID
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = AppNavigationType.NAVIGATION_RAIL
            contentType = AppContentType.GRID
        }
        else -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
            contentType = AppContentType.LIST
        }
    }


    NavGraph(
        navController = navController,
        navigationType = navigationType,
        contentType = contentType,
        isDarkTheme = isDarkTheme,
        setDarkTheme = setDarkTheme
    )
}