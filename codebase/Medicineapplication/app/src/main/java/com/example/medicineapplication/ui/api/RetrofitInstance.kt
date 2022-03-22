package com.example.medicineapplication.ui.api

import com.google.gson.Gson
import com.example.medicineapplication.ui.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Instance of Retrofit Client
object RetrofitInstance {
    val simpleApiClient: SimpleApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())//JSON-Object conversion
        .build()
        .create(SimpleApi::class.java)
}