package com.example.nutritionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nutritionapp.data.database.dao.DBProfessionalsDao
import com.example.nutritionapp.data.database.model.DBProfessionals

@Database(
    entities = [DBProfessionals::class],
    version = 1,
)
abstract class Database : RoomDatabase() {
    abstract fun professionalDao(): DBProfessionalsDao
}