package com.example.nutritionapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.nutritionapp.R

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    rating: Float,
) {
    val fullStars = rating.toInt()
    val hasHalfStar = rating - fullStars >= 0.5f

    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            Icon(
                imageVector = when {
                    i <= fullStars -> Icons.Default.Star
                    i == fullStars + 1 && hasHalfStar -> Icons.Default.Star
                    else -> Icons.Outlined.Star
                },
                contentDescription = stringResource(R.string.stars),
                tint = if (i <= fullStars || (i == fullStars + 1 && hasHalfStar)) Color(87, 185,255) else Color.Gray,
            )
        }
    }
}