package com.example.nutritionapp.core.network

import okhttp3.Headers

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val data: T?, val headers: Headers? = null) : NetworkResult<T>()
    class Error<T : Any> (val e: Throwable) : NetworkResult<T>()
    class Exception<T : Any> (val e: Throwable) : NetworkResult<T>()
}