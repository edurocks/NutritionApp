package com.example.nutritionapp

import android.app.Application
import com.example.nutritionapp.data.di.databaseModule
import com.example.nutritionapp.data.di.networkModule
import com.example.nutritionapp.data.di.professionalsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NutritionApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NutritionApplication)
            modules(networkModule, professionalsModule, databaseModule)
        }
    }
}