package com.example.medicineapplication.ui.api

import com.example.medicineapplication.ui.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
//https://jsonplaceholder.typicode.com/
interface SimpleApi {
    @GET("comments/{id}")
    suspend fun getRequest(@Path("id") id: String): Response<Post>
}