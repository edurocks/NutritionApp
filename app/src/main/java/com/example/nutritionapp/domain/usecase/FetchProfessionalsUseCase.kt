package com.example.nutritionapp.domain.usecase

import com.example.nutritionapp.core.Constants
import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.core.utils.convertSortOptionIntoValue
import com.example.nutritionapp.domain.database.NutritionDB
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FetchProfessionalsUseCase(
    private val professionalsRepository: ProfessionalsRepository,
    private val nutritionDB: NutritionDB
) {

    operator fun invoke(sortedBy: SortOptions, offset: Int): Flow<ProfessionalsResult> {
        return flow {
            val localProfessionals = nutritionDB.getAllProfessionals(
                sortOptions = convertSortOptionIntoValue(sortedBy),
                offset = offset,
                limit = Constants.PROFESSIONALS_LIMIT
            )

            if (localProfessionals.isNotEmpty()) {
                emit(ProfessionalsResult.Success(professionals = localProfessionals))
            } else {
                val remoteProfessionals = fetchProfessionals(sortedBy = sortedBy, offset = offset)
                when (remoteProfessionals) {
                    is ProfessionalsResult.Error -> emit(ProfessionalsResult.Error(exception = remoteProfessionals.exception))
                    is ProfessionalsResult.Success -> {
                        nutritionDB.saveProfessionals(
                            professionals = remoteProfessionals.professionals,
                            sortOptions = convertSortOptionIntoValue(sortedBy),
                            offset = offset,
                            limit = Constants.PROFESSIONALS_LIMIT
                        )
                        emit(ProfessionalsResult.Success(professionals = remoteProfessionals.professionals))
                    }
                }
            }
        }
    }

    private suspend fun fetchProfessionals(sortedBy: SortOptions, offset: Int): ProfessionalsResult {
        return suspendCancellableCoroutine { continuation ->
            professionalsRepository.getProfessionals(
                sortedBy = sortedBy,
                offset = offset,
                professionalsCallback = { professionals ->
                    continuation.resume(ProfessionalsResult.Success(professionals = professionals))
                },
                professionalsErrorCallback = { throwable ->
                    continuation.resume(ProfessionalsResult.Error(exception = throwable))
                }
            )
        }
    }
}