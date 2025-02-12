package com.example.nutritionapp.data.di

import com.example.nutritionapp.presentation.professionals_list.ProfessionalsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val professionalsModule = module {
    viewModelOf(::ProfessionalsViewModel)
}