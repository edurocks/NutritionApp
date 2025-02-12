package com.example.nutritionapp.presentation.professional_detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nutritionapp.R
import com.example.nutritionapp.presentation.components.AboutMe
import com.example.nutritionapp.presentation.components.RatingBar
import com.example.nutritionapp.presentation.professionals_list.state.ProfessionalsState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProfessionalDetailScreen(
    modifier: Modifier = Modifier,
    state: ProfessionalsState,
    onArrowBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(modifier = modifier.fillMaxSize()) {
        state.selectedProfessional?.let { selectedProfessional ->
            Surface(modifier = modifier
                .fillMaxWidth()
                .height(180.dp),
                tonalElevation = 10.dp)
            {
                Column {
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { onArrowBackClick() }
                            .padding(start = 10.dp),
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )

                    Row(modifier = Modifier.padding(top = 10.dp)) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(80.dp)
                                .border(
                                    color = Color.LightGray,
                                    width = 2.dp
                                ).sharedElement(
                                    state = rememberSharedContentState(key = "image/${selectedProfessional.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    }
                                 ),
                            model = selectedProfessional.profilePicture,
                            error = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = stringResource(R.string.professional_image)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                modifier = Modifier.sharedElement(
                                    state = rememberSharedContentState(key = "text/${selectedProfessional.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    }
                                ),
                                text = selectedProfessional.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row {
                                RatingBar(
                                    modifier = Modifier.padding(top = 5.dp),
                                    rating = selectedProfessional.rating.toFloat()
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        start = 6.dp,
                                        top = 7.dp,
                                    ),
                                    fontSize = 10.sp,
                                    text = "${selectedProfessional.rating}/5 (${selectedProfessional.ratingCount})",
                                )
                            }
                        }
                    }
                }
            }

            AboutMe(personalInformation = selectedProfessional.personalInformation)
        }
    }
}