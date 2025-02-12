package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.domain.database.NutritionDB
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchProfessionalsUseCaseTest {

    private var professionalsRepository: ProfessionalsRepository = mockk()
    private var nutritionDB: NutritionDB = mockk()
    private lateinit var fetchProfessionalsUseCase: FetchProfessionalsUseCase

    @Before
    fun setUp() {
        fetchProfessionalsUseCase = FetchProfessionalsUseCase(
            professionalsRepository = professionalsRepository,
            nutritionDB = nutritionDB
        )
    }

    @Test
    fun `invoke should return success from remote when available and save to db`() = runBlocking {
        val remoteProfessionals = listOf(Professional(
            id = 1,
            name = "John Doe",
            rating = 5,
            ratingCount = 10,
            languages = "English",
            expertise = listOf("Nutrition"),
            profilePicture = "",
            personalInformation = "about me"
        ))

        coEvery { nutritionDB.saveProfessionals(
            professionals = any(),
            sortOptions = any(),
            offset = any(),
            limit = any()
        ) } returns Unit

        every { professionalsRepository.getProfessionals(
            sortedBy = any(),
            offset = any(),
            professionalsCallback = any(),
            professionalsErrorCallback = any()
        ) } answers {
            thirdArg<(List<Professional>) -> Unit>().invoke(remoteProfessionals)
        }

        coEvery { nutritionDB.getAllProfessionals(sortOptions = any(), offset = any(), limit = any()) } returns emptyList()

        val result = fetchProfessionalsUseCase(sortedBy = SortOptions.BEST_MATCH, offset = 0).first()

        assert(result is ProfessionalsResult.Success)
        assertEquals(remoteProfessionals, (result as ProfessionalsResult.Success).professionals)
    }

    @Test
    fun `invoke should return error from remote when local data not available`() = runBlocking {

        coEvery { nutritionDB.saveProfessionals(
            professionals = any(),
            sortOptions = any(),
            offset = any(),
            limit = any()
        ) } returns Unit

        every { professionalsRepository.getProfessionals(
            sortedBy = any(),
            offset = any(),
            professionalsCallback = any(),
            professionalsErrorCallback = any()
        ) } answers {
            lastArg<(Throwable) -> Unit>().invoke(Throwable())
        }

        coEvery { nutritionDB.getAllProfessionals(sortOptions = any(), offset = any(), limit = any()) } returns emptyList()

        val result = fetchProfessionalsUseCase(sortedBy = SortOptions.BEST_MATCH, offset = 0).first()

        assert(result is ProfessionalsResult.Error)
    }

    @Test
    fun `invoke should return success from local when remote fails`() = runBlocking {
        val localProfessionals = listOf(Professional(
            id = 1,
            name = "John Doe",
            rating = 5,
            ratingCount = 10,
            languages = "English",
            expertise = listOf("Nutrition"),
            profilePicture = "",
            personalInformation = "about me"
        ))

        every { professionalsRepository.getProfessionals(
            sortedBy = any(),
            offset = any(),
            professionalsCallback = any(),
            professionalsErrorCallback = any()
        )} answers {
            lastArg<(Throwable) -> Unit>().invoke(Throwable())
        }

        coEvery { nutritionDB.getAllProfessionals(
            sortOptions = any(),
            offset = any(),
            limit = any()
        ) } returns localProfessionals

        val result = fetchProfessionalsUseCase(sortedBy = SortOptions.BEST_MATCH, offset = 0).first()

        assert(result is ProfessionalsResult.Success)
        assertEquals(localProfessionals, (result as ProfessionalsResult.Success).professionals)
    }
}