package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.domain.model.Professional

sealed interface ProfessionalsResult {
    data class Success(val professionals: List<Professional>) : ProfessionalsResult
    data class Error(val exception: Throwable) : ProfessionalsResult
}