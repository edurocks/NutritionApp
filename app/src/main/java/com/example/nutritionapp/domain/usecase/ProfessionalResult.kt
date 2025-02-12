package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.domain.model.ProfessionalDetail

sealed interface ProfessionalResult {
    data class Success(val professionalDetail: ProfessionalDetail) : ProfessionalResult
    data class Error(val exception: Throwable) : ProfessionalResult
}