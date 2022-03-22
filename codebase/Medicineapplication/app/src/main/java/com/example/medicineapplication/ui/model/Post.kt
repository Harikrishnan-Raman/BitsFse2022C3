package com.example.medicineapplication.ui.model

// from https://jsonplaceholder.typicode.com/comments
data class Post(
    val id: Int,
    val name: String,
    val postId : Int,
    val email: String,
    val body: String,
)