package com.example.wirelessandmobiletestapplication.ViewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wirelessandmobiletestapplication.Model.Countries
import com.example.wirelessandmobiletestapplication.Model.Country
import com.example.wirelessandmobiletestapplication.Model.listType
import com.example.wirelessandmobiletestapplication.Network.ServiceFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class CountriesViewModel(val sharedPreferences: SharedPreferences) : ViewModel() {
    var gson = Gson()
    private val _filteredCountries: MutableStateFlow<List<Country>> = MutableStateFlow(listOf())
    private val _countriesData: MutableStateFlow<List<Country>> = MutableStateFlow(listOf())
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _error: MutableStateFlow<String> = MutableStateFlow(value = "")
    private val _countryData = MutableLiveData<Country>()
    val loading: StateFlow<Boolean> = _loading
    val countriesData: StateFlow<List<Country>> = _countriesData
    val filteredCountriesData: StateFlow<List<Country>> = _filteredCountries
    val countryData: LiveData<Country> = _countryData
    val error: StateFlow<String> = _error

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> = _search

    init {
        if (_countriesData.value.isEmpty()) retrieveCountriesData()
    }

    fun onSearch(value: String) {
        _search.value = value
        if (value.isNotEmpty()) {
            _loading.value = true
            _filteredCountries.value = _countriesData.value.filter {
                it.name.official.lowercase(Locale.getDefault())
                    .contains(value.lowercase(Locale.getDefault()))
                        ||
                        it.name.common.lowercase(Locale.getDefault())
                            .contains(value.lowercase(Locale.getDefault()))
                        ||
                        (it.capital?.get(0)?.lowercase(Locale.getDefault())
                            ?.contains(value.lowercase(Locale.getDefault())) ?: false)
            }
            _loading.value = false
        }
    }

    fun selectCountry(country: Country) {
        _countryData.value = country
    }

    fun retrieveCountriesData() {
        viewModelScope.launch {
            _loading.value = true
            val storedCountries = sharedPreferences.getString("countries", "")
            if (storedCountries?.isNotBlank() == true) {
                _countriesData.value = gson.fromJson(storedCountries, listType)
                _loading.value = false
            } else {
                val call: Call<Countries> = ServiceFactory.serviceInteractor.getAllCountries()
                call.enqueue(object : Callback<Countries> {
                    override fun onResponse(
                        call: Call<Countries>,
                        response: Response<Countries>
                    ) {
                        if (response.isSuccessful) {
                            val responseData: List<Country>? = response.body()
                            if (responseData != null) {
                                _countriesData.value = responseData
                                _loading.value = false
                                sharedPreferences.edit()
                                    .putString("countries", gson.toJson(responseData)).apply()
                            }
                        } else {
                            _loading.value = false
                            _error.value = "Error al cargar los paises"
                        }
                    }

                    override fun onFailure(call: Call<Countries>, t: Throwable) {
                        _loading.value = false
                        _error.value = "Error al cargar los paises"
                    }
                })
            }
        }
    }
}