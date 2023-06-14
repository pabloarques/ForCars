package com.example.forcars.common

sealed class ResultType<T> {
    data class Success<T>(val data: T) : ResultType<T>()
    data class Error<T>(val message: String?) : ResultType<T>()
}