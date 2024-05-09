package com.example.wirelessandmobiletestapplication.ui.countries

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.wirelessandmobiletestapplication.Model.Country
import com.example.wirelessandmobiletestapplication.ViewModel.CountriesViewModel

@Composable
fun CountriesListScreen(viewModel: CountriesViewModel, navController: NavController) {
    val isLoading = viewModel.loading.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        SearchInput(viewModel)
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            CountriesList(viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput(viewModel: CountriesViewModel) {
    val search: String by viewModel.search.observeAsState(initial = "")
    TextField(value = search,
        onValueChange = { viewModel.onSearch(it) },
        placeholder = { Text(text = "Buscar por nombre o por capital") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CountriesList(viewModel: CountriesViewModel, navHostController: NavController) {
    val countriesData = viewModel.countriesData.collectAsState()
    val filteredCountriesData = viewModel.filteredCountriesData.collectAsState()
    val search = viewModel.search.observeAsState()
    val isNotFiltered = search.value.isNullOrBlank()
    val error = viewModel.error.collectAsState()
    val list = if (!isNotFiltered) filteredCountriesData.value
    else countriesData.value
    Text(
        text = "Paises:",
        modifier = Modifier.padding(16.dp),
        style = TextStyle(fontWeight = FontWeight(700), fontSize = 24.sp)
    )
    if (error.value.isNotBlank()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = error.value)
            Button(
                onClick = { viewModel.retrieveCountriesData() }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Reintentar")
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                )
        ) {
            items(count = list.size) {
                CountryCard(country = list[it], viewModel, navHostController)
            }
        }
    }
}


@Composable
fun CountryCard(country: Country, viewModel: CountriesViewModel, navHostController: NavController) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {

                val strokeWidth = 0.5f * density
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray, Offset(0f, y), Offset(size.width, y), strokeWidth
                )
            }
            .clickable {
                viewModel.selectCountry(country)
                navHostController.navigate("detail_country")
            }) {
        Image(
            contentScale = ContentScale.Fit,
            painter = rememberAsyncImagePainter(country.flags.png),
            contentDescription = country.flags.alt,
            modifier = Modifier
                .height(56.dp)
                .width(56.dp)
                .padding(8.dp)
        )
        Column {
            Text(
                text = country.name.official, style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight(500)
                )
            )
            Text(
                text = country.capital?.get(0) ?: "Capital", style = TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight(300)
                )
            )
        }
    }
}
