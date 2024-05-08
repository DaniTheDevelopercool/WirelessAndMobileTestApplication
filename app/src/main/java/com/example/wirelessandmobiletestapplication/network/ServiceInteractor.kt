package com.example.wirelessandmobiletestapplication.network

import com.example.wirelessandmobiletestapplication.models.Countries
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInteractor {
    @GET("all")
    fun getAllCountries():Call<Countries>
}