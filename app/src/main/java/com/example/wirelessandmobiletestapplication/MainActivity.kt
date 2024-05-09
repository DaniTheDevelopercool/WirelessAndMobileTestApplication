package com.example.wirelessandmobiletestapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wirelessandmobiletestapplication.Navigation.Navigation
import com.example.wirelessandmobiletestapplication.ui.theme.WirelessAndMobileTestApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WirelessAndMobileTestApplicationTheme {
                Navigation(application.getSharedPreferences("storage", Context.MODE_PRIVATE)) //navegación entre pantallas
            }
        }
    }
}