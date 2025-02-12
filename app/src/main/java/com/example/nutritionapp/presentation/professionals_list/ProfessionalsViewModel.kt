package com.example.nutritionapp.presentation.professionals_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritionapp.core.Constants
import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.domain.usecase.FetchProfessionalUseCase
import com.example.nutritionapp.domain.usecase.FetchProfessionalsUseCase
import com.example.nutritionapp.domain.usecase.ProfessionalResult
import com.example.nutritionapp.domain.usecase.ProfessionalsResult
import com.example.nutritionapp.presentation.professionals_list.action.ProfessionalsListAction
import com.example.nutritionapp.presentation.professionals_list.event.ProfessionalsEvent
import com.example.nutritionapp.presentation.professionals_list.state.ProfessionalsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfessionalsViewModel(
    private val fetchProfessionalsUseCase: FetchProfessionalsUseCase,
    private val fetchProfessionalUseCase: FetchProfessionalUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfessionalsState())
    val state = _state
        .onStart { getProfessionals() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ProfessionalsState()
        )

    private val _events = Channel<ProfessionalsEvent>()
    val events = _events.receiveAsFlow()

     private fun getProfessionals(
        initialLoading: Boolean = true,
        sortOptions: SortOptions = SortOptions.BEST_MATCH,
        offset: Int = Constants.PROFESSIONALS_OFFSET,
        selectedFilter: Boolean = false
    ) {
        viewModelScope.launch {
            _state.update { it.copy(initialLoading = initialLoading, listLoading = selectedFilter) }
            fetchProfessionalsUseCase(sortedBy = sortOptions, offset = offset).collect { result ->
                when (result) {
                    is ProfessionalsResult.Success -> {
                        val professionalsList = _state.value.professionals + result.professionals
                        _state.update { it.copy(initialLoading = false, listLoading = false, professionals = professionalsList, offset = offset) }
                    }

                    is ProfessionalsResult.Error -> {
                        _state.update { it.copy(initialLoading = false, listLoading = false) }
                        _events.send(ProfessionalsEvent.OnProfessionalsListResultError)
                    }
                }
            }
        }
    }

    fun onAction(action: ProfessionalsListAction) {
        when (action) {
           is ProfessionalsListAction.OnProfessionalClicked -> {
               getProfessionalDetails(professionalId = action.professionalId)
           }

            is ProfessionalsListAction.OnFilterClicked -> {
                _state.update { it.copy(professionals = emptyList(), category = action.sortOptions, offset = 0) }
                getProfessionals(initialLoading = false, sortOptions = action.sortOptions, selectedFilter = true)
            }

            is ProfessionalsListAction.OnPaginateProfessionalsList -> {
                getProfessionals(initialLoading = false, sortOptions = action.sortOptions, offset = _state.value.offset + 1, selectedFilter = false)
            }
        }
    }

    private fun getProfessionalDetails(professionalId: Int) {
        viewModelScope.launch {
            fetchProfessionalUseCase(professionalId = professionalId).collect { result ->
                when (result) {
                    is ProfessionalResult.Success -> {
                        _state.update { it.copy(selectedProfessional = result.professionalDetail) }
                        _events.send(ProfessionalsEvent.OpenProfessionalDetail)
                    }

                    is ProfessionalResult.Error -> {
                        _events.send(ProfessionalsEvent.OnProfessionalDetailError)
                    }
                }
            }
        }
    }
}