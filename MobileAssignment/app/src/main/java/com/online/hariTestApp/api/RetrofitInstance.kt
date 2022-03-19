package com.pgpfse.hariTestApp.api

import com.google.gson.Gson
import com.pgpfse.hariTestApp.utils.Constants.Companion.BASE_URL
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