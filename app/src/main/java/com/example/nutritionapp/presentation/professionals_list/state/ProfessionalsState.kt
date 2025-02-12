package com.example.nutritionapp.presentation.professionals_list.state

import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail

data class ProfessionalsState(
    val initialLoading: Boolean = false,
    val listLoading: Boolean = false,
    val professionals: List<Professional> = emptyList(),
    val selectedProfessional: ProfessionalDetail? = null,
    val offset: Int = 0,
    val category: SortOptions = SortOptions.BEST_MATCH,
)
