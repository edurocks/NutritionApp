package com.example.nutritionapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalsResponse(
    val count: Int,
    val offset: Int,
    val limit: Int,
    val professionals: List<ProfessionalDetailResponse>
)
