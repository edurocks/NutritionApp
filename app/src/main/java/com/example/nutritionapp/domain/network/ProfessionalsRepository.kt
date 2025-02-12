package com.example.nutritionapp.domain.network

import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail

interface ProfessionalsRepository {

    fun getProfessionals(
        sortedBy: SortOptions,
        offset: Int,
        professionalsCallback: (result: List<Professional>) -> Unit,
        professionalsErrorCallback: (result: Throwable) -> Unit
    )

    fun getProfessional(
        professionalId: Int,
        professionalCallback: (result: ProfessionalDetail) -> Unit,
        professionalErrorCallback: (result: Throwable) -> Unit
    )
}