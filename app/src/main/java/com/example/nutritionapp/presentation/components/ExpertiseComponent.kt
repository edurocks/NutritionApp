package com.example.nutritionapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpertiseComponent(
    modifier: Modifier = Modifier,
    expertise: List<String>
) {
    Row {
        expertise.forEach { expertise ->
            Surface(modifier = modifier.padding(4.dp)) {
                Text(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(5.dp),
                    text = expertise,
                    fontSize = 10.sp,
                    color = Color.Black,
                )
            }
        }
    }
}