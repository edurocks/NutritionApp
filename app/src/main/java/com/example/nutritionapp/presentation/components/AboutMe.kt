package com.example.nutritionapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionapp.R

@Composable
fun AboutMe(
    modifier: Modifier = Modifier,
    personalInformation: String,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val iconRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Text(
        modifier = modifier.padding(start = 10.dp, top = 10.dp),
        text = stringResource(R.string.about_me),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        modifier = modifier
            .padding(top = 10.dp)
            .animateContentSize(),
        text = personalInformation,
        fontSize = 30.sp,
        lineHeight = TextUnit(40f, TextUnitType.Sp),
        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
        overflow = TextOverflow.Ellipsis
    )

    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { isExpanded = !isExpanded },
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(50.dp)
                .rotate(iconRotation),
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = stringResource(R.string.arrow),
        )
    }
}