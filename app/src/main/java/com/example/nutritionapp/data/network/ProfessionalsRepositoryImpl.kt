package com.example.nutritionapp.data.network

import com.example.nutritionapp.core.Constants
import com.example.nutritionapp.core.network.handleApi
import com.example.nutritionapp.core.network.onError
import com.example.nutritionapp.core.network.onException
import com.example.nutritionapp.core.network.onSuccess
import com.example.nutritionapp.core.utils.SortOptions
import com.example.nutritionapp.core.utils.convertSortOptionIntoValue
import com.example.nutritionapp.data.mappers.toProfessionalDetail
import com.example.nutritionapp.data.mappers.toProfessionals
import com.example.nutritionapp.domain.model.Professional
import com.example.nutritionapp.domain.model.ProfessionalDetail
import com.example.nutritionapp.domain.network.ProfessionalsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfessionalsRepositoryImpl(
    private val professionalsApi: ProfessionalsApi
) : ProfessionalsRepository {

    override fun getProfessionals(
        sortedBy: SortOptions,
        offset: Int,
        professionalsCallback: (result: List<Professional>) -> Unit,
        professionalsErrorCallback: (result: Throwable) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = handleApi {
                professionalsApi.getProfessionals(
                    limit = Constants.PROFESSIONALS_LIMIT,
                    offset = offset,
                    sortBy = convertSortOptionIntoValue(sortedBy)
                )
            }
            call.onSuccess { professionalsResponse, _ ->
                professionalsResponse?.let { professionalsData ->
                    professionalsCallback.invoke(professionalsData.professionals.toProfessionals())
                }
            }.onError { throwable ->
                professionalsErrorCallback.invoke(throwable)
            }.onException { throwable ->
                professionalsErrorCallback.invoke(throwable)
            }
        }
    }

    override fun getProfessional(
        professionalId: Int,
        professionalCallback: (result: ProfessionalDetail) -> Unit,
        professionalErrorCallback: (result: Throwable) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = handleApi {
                professionalsApi.getProfessional(id = professionalId)
            }
            call.onSuccess { professionalResponse, _ ->
                professionalResponse?.let { professionalData ->
                    professionalCallback.invoke(professionalData.toProfessionalDetail())
                }
            }.onError { throwable ->
                professionalErrorCallback.invoke(throwable)
            }.onException { throwable ->
                professionalErrorCallback.invoke(throwable)
            }
        }
    }
}