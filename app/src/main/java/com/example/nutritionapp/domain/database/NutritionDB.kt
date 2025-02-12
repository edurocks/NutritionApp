package com.example.nutritionapp.domain.database

import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail

interface NutritionDB {
    suspend fun getAllProfessionals(sortOptions: String, offset: Int, limit: Int): List<Professional>
    suspend fun getProfessionalDetails(professionalId: Int): ProfessionalDetail?
    suspend fun saveProfessionals(professionals: List<Professional>, sortOptions: String, offset: Int, limit: Int)
}