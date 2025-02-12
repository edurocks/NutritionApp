package com.example.nutritionapp.presentation.professionals_list.event

sealed interface ProfessionalsEvent {
    data object OpenProfessionalDetail : ProfessionalsEvent
    data object OnProfessionalDetailError : ProfessionalsEvent
    data object OnProfessionalsListResultError : ProfessionalsEvent
}