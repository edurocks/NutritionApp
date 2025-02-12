@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.nutritionapp.presentation

import app.cash.turbine.test
import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail
import com.example.nutritionapp.domain.usecase.FetchProfessionalUseCase
import com.example.nutritionapp.domain.usecase.FetchProfessionalsUseCase
import com.example.nutritionapp.domain.usecase.ProfessionalResult
import com.example.nutritionapp.domain.usecase.ProfessionalsResult
import com.example.nutritionapp.presentation.professionals_list.ProfessionalsViewModel
import com.example.nutritionapp.presentation.professionals_list.action.ProfessionalsListAction
import com.example.nutritionapp.presentation.professionals_list.event.ProfessionalsEvent
import com.example.nutritionapp.rules.CoroutinesMainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfessionalsViewModelTest {

    private lateinit var fetchProfessionalsUseCase: FetchProfessionalsUseCase
    private lateinit var fetchProfessionalUseCase: FetchProfessionalUseCase
    private lateinit var viewModel: ProfessionalsViewModel

    @get:Rule
    val mainDispatcherRule = CoroutinesMainDispatcherRule()

    @Before
    fun setUp() {
        fetchProfessionalsUseCase = mockk()
        fetchProfessionalUseCase = mockk()
        viewModel = ProfessionalsViewModel(
            fetchProfessionalsUseCase = fetchProfessionalsUseCase,
            fetchProfessionalUseCase = fetchProfessionalUseCase
        )
    }

    @Test
    fun `getProfessionals should update state with professionals when successful`() = runTest {
        val professionals = listOf(
            Professional(
                id = 1,
                name = "John Doe",
                rating = 4,
                ratingCount = 100,
                profilePicture = "",
                languages = "English",
                expertise = listOf("expertise"),
                personalInformation = "about me"
            ))

        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Success(
                professionals
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnPaginateProfessionalsList(sortOptions = SortOptions.BEST_MATCH))

        advanceUntilIdle()

        assertEquals(professionals.first(), viewModel.state.value.professionals.first())
        assertEquals(false, viewModel.state.value.initialLoading)
    }

    @Test
    fun `onFilterClicked should reset state and load filtered professionals`() = runTest {
        val professionals = listOf(Professional(
            id = 1,
            name = "John Doe",
            rating = 4,
            ratingCount = 100,
            profilePicture = "",
            languages = "English",
            expertise = listOf("expertise"),
            personalInformation = "about me"
        ))

        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Success(
                professionals = professionals
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnFilterClicked(sortOptions = SortOptions.BEST_MATCH))

        assertEquals(emptyList<Professional>(), viewModel.state.value.professionals)
        assertEquals(SortOptions.BEST_MATCH, viewModel.state.value.category)
    }

    @Test
    fun `onProfessionalClicked should fetch professional details and update state`() = runTest {
        every { fetchProfessionalsUseCase(any(), any()) } returns flowOf(ProfessionalsResult.Success(emptyList()))

        val professionalDetail = ProfessionalDetail(
            id = 1,
            name = "John Doe",
            rating = 4,
            ratingCount = 100,
            profilePicture = "",
            personalInformation = "about me"
        )

        every { fetchProfessionalUseCase(professionalId = any()) } answers {
            flowOf(
                ProfessionalResult.Success(
                    professionalDetail
                )
            )
        }

        viewModel.onAction(ProfessionalsListAction.OnProfessionalClicked(professionalId = 1))

        advanceUntilIdle()

        assertEquals(professionalDetail, viewModel.state.value.selectedProfessional)
    }

    @Test
    fun `onProfessionalClicked should not update state when fetchProfessionalsUseCase fails`() = runTest {
        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Error(
                Throwable("Error")
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnPaginateProfessionalsList(sortOptions = SortOptions.BEST_MATCH))

        advanceUntilIdle()

        assertTrue(viewModel.state.value.professionals.isEmpty())
    }


    @Test
    fun `onProfessionalClicked should not update state when fetchProfessionalUseCase fails`() = runTest {
        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Success(emptyList()
            )
        )

        coEvery { fetchProfessionalUseCase(professionalId = any()) } returns flowOf(
            ProfessionalResult.Error(
                Throwable("Error")
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnProfessionalClicked(professionalId = 1))

        advanceUntilIdle()

        assertEquals(null, viewModel.state.value.selectedProfessional)
    }

    @Test
    fun `onProfessionalClicked should emit event when fetchProfessionalUseCase fails`() = runTest {
        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Success(emptyList()
            )
        )

        coEvery { fetchProfessionalUseCase(professionalId = any()) } returns flowOf(
            ProfessionalResult.Error(
                Throwable("Error")
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnProfessionalClicked(professionalId = 1))

        channelFlow {
            send(ProfessionalsEvent.OnProfessionalDetailError)
        }.test {
            assertEquals(ProfessionalsEvent.OnProfessionalDetailError, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `onProfessionalClicked should emit event when fetchProfessionalsUseCase fails`() = runTest {
        every { fetchProfessionalsUseCase(sortedBy = any(), offset = any()) } returns flowOf(
            ProfessionalsResult.Error(
                Throwable("Error")
            )
        )

        viewModel.onAction(ProfessionalsListAction.OnPaginateProfessionalsList(sortOptions = SortOptions.BEST_MATCH))

        channelFlow {
            send(ProfessionalsEvent.OnProfessionalsListResultError)
        }.test {
            assertEquals(ProfessionalsEvent.OnProfessionalsListResultError, awaitItem())
            awaitComplete()
        }
    }
}