package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.preference.PreferenceManager
import com.example.shoppinglist.ui.ShoppingListApp
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var _sharedPreferences: SharedPreferences? = null
    private val sharedPreferences
        get() = _sharedPreferences
            ?: throw IllegalStateException("SharedPreferences must not be null")

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPreferences.edit()

        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(sharedPreferences.getBoolean("isDarkTheme", false)) }
            val setDarkTheme = {
                editor.putBoolean("isDarkTheme", !isDarkTheme)
                editor.apply()
                isDarkTheme = !isDarkTheme
            }

            ShoppingListTheme(darkTheme = isDarkTheme) {
                val windowSize = calculateWindowSizeClass(this)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingListApp(
                        windowSize = windowSize.widthSizeClass,
                        isDarkTheme = isDarkTheme,
                        setDarkTheme = setDarkTheme
                    )
                }
            }
        }
    }
}

