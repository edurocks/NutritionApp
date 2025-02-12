package com.example.nutritionapp.presentation.professionals_list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.core.utils.convertValueIntoSortOption
import com.example.nutritionapp.core.utils.formatSelectedSortOptions
import com.example.nutritionapp.core.utils.formatSortOptions
import com.example.nutritionapp.presentation.components.LoadingIndicator
import com.example.nutritionapp.presentation.components.ProfessionalsListEmptyScreen
import com.example.nutritionapp.presentation.components.SortDropdown
import com.example.nutritionapp.presentation.professionals_list.action.ProfessionalsListAction
import com.example.nutritionapp.presentation.professionals_list.state.ProfessionalsState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProfessionalsListScreen(
    modifier: Modifier = Modifier,
    state: ProfessionalsState,
    onAction: (ProfessionalsListAction) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val lazyListState = rememberLazyListState()

    when {

        state.initialLoading -> LoadingIndicator(modifier = modifier.fillMaxSize())

        else -> {

            Column {
                SortDropdown(
                    modifier = Modifier.padding(top = 60.dp),
                    sortOptions = listOf(formatSortOptions(SortOptions.BEST_MATCH.name),
                        formatSortOptions(SortOptions.MOST_POPULAR.name),
                        formatSortOptions(SortOptions.RATING.name)
                    ),
                    onSortOptionSelected = {
                        onAction(ProfessionalsListAction.OnFilterClicked(
                                sortOptions = convertValueIntoSortOption(
                                    formatSelectedSortOptions(it)
                                )
                            )
                        )
                    }
                )

                if (!state.listLoading && state.professionals.isEmpty()) ProfessionalsListEmptyScreen()

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 10.dp, bottom = 50.dp, end = 10.dp),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.listLoading) {
                        item { LoadingIndicator(modifier = Modifier.fillParentMaxSize()) }
                    } else {
                        itemsIndexed(items = state.professionals) { index, professional ->
                            if (index >= state.professionals.size - 1) {
                                onAction(ProfessionalsListAction.OnPaginateProfessionalsList(sortOptions = state.category))
                            }
                            ProfessionalsListItem(
                                professionalData = professional,
                                onClick = {
                                    onAction(ProfessionalsListAction.OnProfessionalClicked(
                                        professionalId = professional.id)
                                    )
                                },
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        }
                    }
                }
            }
        }
    }
}
