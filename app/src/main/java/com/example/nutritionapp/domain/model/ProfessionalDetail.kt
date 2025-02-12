package com.example.nutritionapp.domain.model

data class ProfessionalDetail(
    val id: Int,
    val name: String,
    val rating: Int,
    val ratingCount: Int,
    val profilePicture: String,
    val personalInformation: String
)
