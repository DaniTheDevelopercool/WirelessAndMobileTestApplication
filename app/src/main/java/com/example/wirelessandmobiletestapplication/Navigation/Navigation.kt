package com.example.wirelessandmobiletestapplication.Navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wirelessandmobiletestapplication.ViewModel.CountriesViewModel
import com.example.wirelessandmobiletestapplication.ui.countries.CountriesListScreen
import com.example.wirelessandmobiletestapplication.ui.countries.CountryDetailScreen

@Composable
fun Navigation(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()
    val viewModel = CountriesViewModel(sharedPreferences)
    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable("home") {
            CountriesListScreen(viewModel, navController)
        }
        composable("detail_country") {
            CountryDetailScreen(viewModel, navController)
        }
    }
}