package com.example.wirelessandmobiletestapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {
    private const val BASE_URL = "https://restcountries.com/v3.1/"
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val serviceInteractor : ServiceInteractor by lazy { retrofit.create(ServiceInteractor::class.java) }
}