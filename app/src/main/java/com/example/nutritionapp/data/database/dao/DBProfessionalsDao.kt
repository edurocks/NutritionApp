package com.example.nutritionapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nutritionapp.data.database.model.DBProfessionals

@Dao
interface DBProfessionalsDao {

    @Upsert
    suspend fun saveProfessionals(professionals: List<DBProfessionals>)

    @Query("SELECT * FROM DBProfessionals WHERE category = :sortOptions AND `offset` = :offset LIMIT :limit")
    suspend fun getAllProfessionals(sortOptions: String, offset: Int, limit: Int): List<DBProfessionals>

    @Query("SELECT * FROM DBProfessionals WHERE professional_id = :professionalId")
    suspend fun getProfessionalDetails(professionalId: Int): DBProfessionals
}