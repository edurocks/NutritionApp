package com.example.nutritionapp.presentation.professionals_list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutritionapp.R
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.presentation.components.ExpertiseComponent
import com.example.nutritionapp.presentation.components.RatingBar

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProfessionalsListItem(
    modifier: Modifier = Modifier,
    professionalData: Professional,
    onClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val borderColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick(professionalData.id) }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        color = Color.LightGray,
                        width = 2.dp
                    )
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${professionalData.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
                model = professionalData.profilePicture,
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.professional_image)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "text/${professionalData.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
                    text = professionalData.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    RatingBar(rating = professionalData.rating.toFloat())

                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 1.dp,
                        ),
                        fontSize = 10.sp,
                        text = "${professionalData.rating}/5 (${professionalData.ratingCount})",
                    )
                }

                Row {
                    Icon(
                        modifier = Modifier.size(20.dp)
                            .padding(top = 2.dp),
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(R.string.languages)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = professionalData.languages,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        ExpertiseComponent(expertise = professionalData.expertise)
    }
}