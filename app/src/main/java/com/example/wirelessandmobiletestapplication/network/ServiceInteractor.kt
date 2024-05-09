package com.example.wirelessandmobiletestapplication.Network

import com.example.wirelessandmobiletestapplication.Model.Countries
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInteractor {
    @GET("all")
    fun getAllCountries():Call<Countries>
}