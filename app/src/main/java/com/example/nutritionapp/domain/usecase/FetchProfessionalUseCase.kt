package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.domain.database.NutritionDB
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FetchProfessionalUseCase(
    private val professionalsRepository: ProfessionalsRepository,
    private val nutritionDB: NutritionDB
) {
    operator fun invoke(professionalId: Int): Flow<ProfessionalResult> {
        return flow {
            val localProfessionalDetail = nutritionDB.getProfessionalDetails(professionalId = professionalId)
            localProfessionalDetail?.let {
                emit(ProfessionalResult.Success(localProfessionalDetail))
            } ?: run {
                val remoteProfessionalDetail = fetchProfessional(professionalId = professionalId)
                when (remoteProfessionalDetail) {
                    is ProfessionalResult.Error -> emit(ProfessionalResult.Error(remoteProfessionalDetail.exception))
                    is ProfessionalResult.Success -> emit(ProfessionalResult.Success(remoteProfessionalDetail.professionalDetail))
                }

            }
        }
    }

    private suspend fun fetchProfessional(professionalId: Int): ProfessionalResult {
        return suspendCancellableCoroutine { continuation ->
            professionalsRepository.getProfessional(
                professionalId = professionalId,
                professionalCallback = { professional ->
                    continuation.resume(ProfessionalResult.Success(professional))
                },
                professionalErrorCallback = { throwable ->
                    continuation.resume(ProfessionalResult.Error(throwable))
                }
            )
        }
    }
}