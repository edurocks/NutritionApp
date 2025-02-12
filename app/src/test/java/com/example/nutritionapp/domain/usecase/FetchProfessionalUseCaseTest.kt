package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.domain.database.NutritionDB
import com.example.nutritionapp.domain.model.ProfessionalDetail
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchProfessionalUseCaseTest {

    private var professionalRepository: ProfessionalsRepository = mockk()
    private var nutritionDB: NutritionDB = mockk()
    private lateinit var fetchProfessionalUseCase: FetchProfessionalUseCase

    @Before
    fun setUp() {
        fetchProfessionalUseCase = FetchProfessionalUseCase(
            professionalsRepository = professionalRepository,
            nutritionDB = nutritionDB
        )
    }

    @Test
    fun `invoke should return success from remote when available`() = runTest {
        val professionalDetail = ProfessionalDetail(
            id = 1,
            name = "John Doe",
            rating = 4,
            ratingCount = 100,
            profilePicture = "",
            personalInformation = "about me"
        )

        every { professionalRepository.getProfessional(
            professionalId = any(),
            professionalCallback = any(),
            professionalErrorCallback = any()
        ) } answers {
            secondArg<(ProfessionalDetail) -> Unit>().invoke(professionalDetail)
        }

        coEvery { nutritionDB.getProfessionalDetails(professionalId = any()) } returns null

        val result = fetchProfessionalUseCase(professionalId = professionalDetail.id).first()

        assert(result is ProfessionalResult.Success)
        assertEquals(professionalDetail, (result as ProfessionalResult.Success).professionalDetail)
    }

    @Test
    fun `invoke should return error from remote when not available`() = runTest {
        val professionalDetail = ProfessionalDetail(
            id = 1,
            name = "John Doe",
            rating = 4,
            ratingCount = 100,
            profilePicture = "",
            personalInformation = "about me"
        )

        every { professionalRepository.getProfessional(
            professionalId = any(),
            professionalCallback = any(),
            professionalErrorCallback = any()
        ) } answers {
            thirdArg<(Throwable) -> Unit>().invoke(Throwable())
        }

        coEvery { nutritionDB.getProfessionalDetails(professionalId = any()) } returns null

        val result = fetchProfessionalUseCase(professionalId = professionalDetail.id).first()

        assert(result is ProfessionalResult.Error)
    }

    @Test
    fun `invoke should return success from local when remote fails`() = runTest {
        val professionalDetail = ProfessionalDetail(
            id = 1,
            name = "John Doe",
            rating = 4,
            ratingCount = 100,
            profilePicture = "",
            personalInformation = "about me"
        )

        every { professionalRepository.getProfessional(
            professionalId = any(),
            professionalCallback = any(),
            professionalErrorCallback = any()
        ) } answers {
            thirdArg<(Throwable) -> Unit>().invoke(Throwable())
        }

        coEvery { nutritionDB.getProfessionalDetails(professionalId = any()) } returns professionalDetail

        val result = fetchProfessionalUseCase(professionalId = professionalDetail.id).first()

        assert(result is ProfessionalResult.Success)
        assertEquals(professionalDetail, (result as ProfessionalResult.Success).professionalDetail)
    }
}