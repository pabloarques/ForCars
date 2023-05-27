package com.example.forcars.data.impl

import com.example.forcars.data.ResultType
import com.example.forcars.data.ws.CarsApi
import com.example.forcars.di.module.model.response.CarsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarsRepository @Inject constructor(private val carsApi: CarsApi) {

    suspend fun getCars(): Flow<ResultType<CarsResponse>> = flow {
        try {
            val response = carsApi.getCars().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultType.Success(it.body()!!))
                }
            } else {
                val msg = when (response.code()) {
                    404 -> "No se encontraron resultados"
                    else -> "Error en la petición"
                }
                emit(ResultType.Error(msg))
            }

        } catch (e: Exception) {
            e.message?.let { emit(ResultType.Error(it)) }
        }
    }
}