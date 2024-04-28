package com.example.shoppinglist.ui.screens.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.shoppinglist.ui.navigation.BottomNavigationScreens
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.navigation.currentRoute
import com.example.shoppinglist.ui.theme.ShoppingListTheme

val railNavigationItems = listOf(
    BottomNavigationScreens.Settings,
    BottomNavigationScreens.ShopList,
    BottomNavigationScreens.Notes,
)

@Composable
fun RailNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens> = railNavigationItems,
    floatingActionButtonOnClick: () -> Unit = {}
) {
    val currentRoute = currentRoute(navController)

    NavigationRail {
        items.forEach {
            NavigationRailItem(
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
        Spacer(modifier = Modifier.height(40.dp))
        FloatingActionButton(onClick = floatingActionButtonOnClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(bottomFloatingActionButton.icon),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RailNavigationPreview() {
    ShoppingListTheme {
        Row {
            RailNavigation(navController = rememberNavController())
            Scaffold(
                bottomBar = {
                }
            )
            {
                LazyColumn(
                    modifier = Modifier.padding(it)
                ) {
                    items(listOf("", "", "")) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(7.dp),
                            colors = CardDefaults.cardColors(Color.Cyan),
                            content = {}
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(7.dp),
                            colors = CardDefaults.cardColors(Color.Cyan),
                            content = {}
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(7.dp),
                            colors = CardDefaults.cardColors(Color.Cyan),
                            content = {}
                        )
                    }
                }
            }
        }
    }
}