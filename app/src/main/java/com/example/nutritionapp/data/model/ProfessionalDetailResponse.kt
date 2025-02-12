package com.example.nutritionapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalDetailResponse(
    val id: Int,
    val name: String,
    val rating: Int,
    @SerialName("rating_count")
    val ratingCount: Int,
    val languages: List<String>,
    val expertise: List<String>,
    @SerialName("profile_picture_url")
    val profilePicture: String,
    @SerialName("about_me")
    val personalInformation: String
)
