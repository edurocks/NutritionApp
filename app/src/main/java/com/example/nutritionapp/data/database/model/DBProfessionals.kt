package com.example.nutritionapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["professional_id", "category", "offset"])
data class DBProfessionals(
    @ColumnInfo(name = "professional_id") val professionalId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "rating_count") val ratingCount: Int,
    @ColumnInfo(name = "languages") val languages: String,
    @ColumnInfo(name = "expertise") val expertise: String,
    @ColumnInfo(name = "profile_picture") val profilePicture: String,
    @ColumnInfo(name = "personal_information") val personalInformation: String,
    @ColumnInfo(name = "category") val sortOptions: String,
    @ColumnInfo(name = "offset") val offset: Int,
)