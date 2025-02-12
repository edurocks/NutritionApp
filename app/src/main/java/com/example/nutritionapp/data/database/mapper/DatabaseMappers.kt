package com.example.nutritionapp.data.database.mapper

import com.example.nutritionapp.data.database.model.DBProfessionals
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail

fun DBProfessionals.toProfessional(): Professional = Professional(
    id = this.professionalId,
    name = this.name,
    rating = this.rating,
    ratingCount = this.ratingCount,
    languages = this.languages,
    expertise = this.expertise.split(","),
    profilePicture = this.profilePicture,
    personalInformation = this.personalInformation
)

fun DBProfessionals.toProfessionalDetail(): ProfessionalDetail = ProfessionalDetail(
    id = this.professionalId,
    name = this.name,
    rating = this.rating,
    ratingCount = this.ratingCount,
    profilePicture = this.profilePicture,
    personalInformation = this.personalInformation
)

fun Professional.toDBProfessional(sortOptions: String, offset: Int): DBProfessionals = DBProfessionals(
    professionalId = this.id,
    name = this.name,
    rating = this.rating,
    ratingCount = this.ratingCount,
    languages = this.languages,
    expertise = this.expertise.joinToString(separator = ","),
    profilePicture = this.profilePicture,
    personalInformation = this.personalInformation,
    sortOptions = sortOptions,
    offset = offset,
)