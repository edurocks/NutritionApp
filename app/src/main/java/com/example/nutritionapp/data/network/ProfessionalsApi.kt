package com.example.nutritionapp.data.network

import com.example.nutritionapp.core.Constants
import com.example.nutritionapp.data.model.ProfessionalDetailResponse
import com.example.nutritionapp.data.model.ProfessionalsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfessionalsApi {

    @GET(Constants.URL_PROFESSIONALS_PATH)
    suspend fun getProfessionals(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("sort_by") sortBy: String
    ): Response<ProfessionalsResponse>

    @GET(Constants.URL_PROFESSIONAL_PATH)
    suspend fun getProfessional(
        @Path("id") id: Int,
    ): Response<ProfessionalDetailResponse>
}