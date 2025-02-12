package com.example.nutritionapp.data.database

import com.example.nutritionapp.data.database.mapper.toDBProfessional
import com.example.nutritionapp.data.database.mapper.toProfessional
import com.example.nutritionapp.data.database.mapper.toProfessionalDetail
import com.example.nutritionapp.domain.database.NutritionDB
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail

class NutritionDBImpl(private val db: Database) : NutritionDB {

    override suspend fun getAllProfessionals(
        sortOptions: String,
        offset: Int,
        limit: Int
    ): List<Professional> {
        return db.professionalDao().getAllProfessionals(
            sortOptions = sortOptions,
            offset = offset,
            limit = limit
        ).map { it.toProfessional() }
    }

    override suspend fun getProfessionalDetails(professionalId: Int): ProfessionalDetail {
        return db.professionalDao().getProfessionalDetails(professionalId = professionalId).toProfessionalDetail()
    }

    override suspend fun saveProfessionals(
        professionals: List<Professional>,
        sortOptions: String,
        offset: Int,
        limit: Int
    ) {
        db.professionalDao().saveProfessionals(
            professionals = professionals.map {
                it.toDBProfessional(sortOptions = sortOptions, offset = offset)
            }
        )
    }
}