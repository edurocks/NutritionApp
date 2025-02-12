package com.example.nutritionapp.presentation.professionals_list.action

import com.example.nutritionapp.core.utils.SortOptions

sealed interface ProfessionalsListAction {
    data class OnProfessionalClicked(val professionalId: Int) : ProfessionalsListAction
    data class OnFilterClicked(val sortOptions: SortOptions) : ProfessionalsListAction
    data class OnPaginateProfessionalsList(val sortOptions: SortOptions) : ProfessionalsListAction
}