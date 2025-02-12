package com.example.nutritionapp.data.mappers

import com.example.nutritionapp.data.model.ProfessionalDetailResponse
import com.example.nutritionapp.domain.model.Professional

fun List<ProfessionalDetailResponse>.toProfessionals(): List<Professional> {
    return mapNotNull { professionalDetailResponse ->
        Professional(
            id = professionalDetailResponse.id,
            name = professionalDetailResponse.name,
            rating = professionalDetailResponse.rating,
            ratingCount = professionalDetailResponse.ratingCount,
            languages = professionalDetailResponse.languages.joinToString(separator = ","),
            expertise = professionalDetailResponse.expertise,
            profilePicture = professionalDetailResponse.profilePicture.substringBeforeLast("/"),
            personalInformation = professionalDetailResponse.personalInformation
        )
    }
}