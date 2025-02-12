package com.example.nutritionapp.data.di

import com.example.nutritionapp.core.Constants
import com.example.nutritionapp.data.network.ProfessionalsApi
import com.example.nutritionapp.data.network.ProfessionalsRepositoryImpl
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import com.example.nutritionapp.domain.usecase.FetchProfessionalUseCase
import com.example.nutritionapp.domain.usecase.FetchProfessionalsUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(json.asConverterFactory(Constants.JSON_MEDIA_TYPE.toMediaType()))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ProfessionalsApi::class.java)
    }

    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        ).build()
    }

    singleOf(::ProfessionalsRepositoryImpl) { bind<ProfessionalsRepository>() }

    singleOf(::FetchProfessionalsUseCase)

    singleOf(::FetchProfessionalUseCase)
}