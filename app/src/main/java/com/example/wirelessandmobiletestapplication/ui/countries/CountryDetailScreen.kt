package com.example.wirelessandmobiletestapplication.ui.countries

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.wirelessandmobiletestapplication.ViewModel.CountriesViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(viewModel: CountriesViewModel, navController: NavController) {
    val country = viewModel.countryData.value
    val countriesData = viewModel.countriesData.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = country?.name?.official ?: "Detalle del pais") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(
                        imageVector = Icons.Filled.Home, contentDescription = "Back"
                    )
                }
            })
    }, content = {
        if (country != null) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = country.name.official,
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = TextStyle(fontWeight = FontWeight(700), fontSize = 24.sp)
                )
                Image(
                    contentScale = ContentScale.Fit,
                    painter = rememberAsyncImagePainter(country.flags.png),
                    contentDescription = country.flags.alt,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                )

                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Nombre oficial:",
                        style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                    )
                    Text(
                        text = country.name.official,
                        style = TextStyle(fontWeight = FontWeight(300), fontSize = 14.sp)
                    )
                }
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Nombre común:",
                        style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                    )
                    Text(
                        text = country.name.common,
                        style = TextStyle(fontWeight = FontWeight(300), fontSize = 14.sp)
                    )
                }
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Capital:",
                        style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                    )
                    Text(
                        text = country.capital?.get(0) ?: "Capital",
                        style = TextStyle(fontWeight = FontWeight(300), fontSize = 14.sp)
                    )
                }
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Region:",
                        style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                    )
                    Text(
                        text = country.region.name,
                        style = TextStyle(fontWeight = FontWeight(300), fontSize = 14.sp)
                    )
                }
                if (country.subregion != null) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        Text(
                            text = "Subregion:",
                            style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                        )
                        Text(
                            text = country.subregion,
                            style = TextStyle(fontWeight = FontWeight(300), fontSize = 14.sp)
                        )
                    }
                }
                if (country.borders?.isNotEmpty() == true) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        Text(
                            text = "Paises vecinos:",
                            style = TextStyle(fontWeight = FontWeight(500), fontSize = 16.sp)
                        )
                        LazyColumn {
                            val borders = country.borders
                            items(count = borders.size) { index ->
                                CountryCard(
                                    country = countriesData.value.first {
                                        it.cca3 == borders[index]
                                    }, viewModel, navHostController = navController
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Text(text = "Ha ocurrido un error")
        }
    })
}