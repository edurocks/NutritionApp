package com.example.nutritionapp.core.network

import okhttp3.Headers
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> NetworkResult<T>.onSuccess(
    executable: suspend (T?, Headers?) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success) {
        executable(data, headers)
    }
}

suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend (e: Throwable) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error) {
        executable(e)
    }
}

suspend fun <T : Any> NetworkResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception) {
        executable(e)
    }
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>,
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        val headers = response.headers()

        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body, headers)
        } else {
            NetworkResult.Error(Throwable("Something went wrong with the request..."))
        }
    } catch (e: HttpException) {
        NetworkResult.Error(Throwable(e))
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}
