package com.example.shoppinglist.ui.screens.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.navigation.BottomNavigationScreens
import com.example.shoppinglist.ui.navigation.currentRoute
import com.example.shoppinglist.ui.theme.ShoppingListTheme

val bottomFloatingActionButton = BottomNavigationScreens.AddNewItem

val bottomNavigationItems = listOf(
    BottomNavigationScreens.Settings,
    BottomNavigationScreens.ShopList,
    BottomNavigationScreens.Notes,
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavigationScreens> = bottomNavigationItems,
    floatingActionButtonOnClick: () -> Unit
) {
    val currentRoute = currentRoute(navController)


    BottomAppBar(
        actions = {
            items.forEach {
                NavigationBarItem(
                    selected = currentRoute == it.route,
                    onClick = {
                        if (currentRoute != it.route) {
                            navController.navigate(it.route) {
                                popUpTo(it.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    label = { Text(stringResource(it.label)) },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(it.icon),
                            contentDescription = null
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = floatingActionButtonOnClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(bottomFloatingActionButton.icon),
                    contentDescription = null
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MainBottomNavigationBarPreview() {
    ShoppingListTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navController = rememberNavController(),
                    floatingActionButtonOnClick = { }
                )
            }
        )
        {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Hi", fontSize = 50.sp)
                Text(text = "Hello", fontSize = 50.sp)
                Text(text = "Hi", fontSize = 50.sp)
            }
        }
    }
}