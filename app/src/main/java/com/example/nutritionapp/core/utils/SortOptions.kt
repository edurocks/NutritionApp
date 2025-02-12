package com.example.nutritionapp.core.utils

enum class SortOptions {
    BEST_MATCH,
    RATING,
    MOST_POPULAR,
}

fun convertSortOptionIntoValue(sortOptions: SortOptions): String {
    return when (sortOptions) {
        SortOptions.BEST_MATCH -> "best_match"
        SortOptions.RATING -> "rating"
        SortOptions.MOST_POPULAR -> "most_popular"
    }
}

fun convertValueIntoSortOption(value: String): SortOptions {
    return when (value) {
        "best_match" -> SortOptions.BEST_MATCH
        "most_popular" -> SortOptions.MOST_POPULAR
        "rating" -> SortOptions.RATING
        else -> SortOptions.BEST_MATCH
    }
}