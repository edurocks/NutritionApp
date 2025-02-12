package com.example.nutritionapp.data.di

import androidx.room.Room.databaseBuilder
import com.example.nutritionapp.data.database.Database
import com.example.nutritionapp.data.database.NutritionDBImpl
import com.example.nutritionapp.domain.database.NutritionDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        databaseBuilder(
            context = androidContext(),
            klass = Database::class.java,
            name = "nutritiondb",
        ).build()
    }

    single<NutritionDB> {
        NutritionDBImpl(db = get())
    }
}