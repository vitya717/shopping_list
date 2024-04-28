package com.example.shoppinglist.ui.screens.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.ui.screens.elements.BottomNavigationBar
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.util.AppNavigationType

@Composable
fun SettingsScreen(
    navController: NavHostController,
    navigationType: AppNavigationType,
    isDarkTheme: Boolean,
    setDarkTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Settings", fontSize = 50.sp)
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "go back")
            }

            Switch(
                checked = isDarkTheme, onCheckedChange = { setDarkTheme() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ShoppingListTheme {
//        SettingsScreen(rememberNavController())
    }
}