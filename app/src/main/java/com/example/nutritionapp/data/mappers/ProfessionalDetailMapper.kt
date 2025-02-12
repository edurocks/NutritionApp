package com.example.nutritionapp.data.mappers

import com.example.nutritionapp.data.model.ProfessionalDetailResponse
import com.example.nutritionapp.domain.model.ProfessionalDetail

fun ProfessionalDetailResponse.toProfessionalDetail(): ProfessionalDetail {
    return ProfessionalDetail(
        id = id,
        name = name,
        rating = rating,
        ratingCount = ratingCount,
        profilePicture = profilePicture.substringBeforeLast("/"),
        personalInformation = personalInformation
    )
}
