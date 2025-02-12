package com.example.nutritionapp.domain.model

data class Professional(
    val id: Int,
    val name: String,
    val rating: Int,
    val ratingCount: Int,
    val languages: String,
    val expertise: List<String>,
    val profilePicture: String,
    val personalInformation: String
)
