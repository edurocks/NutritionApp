package com.example.nutritionapp.core.utils

import java.util.Locale

fun formatSortOptions(input: String): String {
    return input.split("_")
        .joinToString(" ") { part ->
            part.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
        }
}

fun formatSelectedSortOptions(input: String): String {
    return input.split(" ").joinToString("_").lowercase()
}