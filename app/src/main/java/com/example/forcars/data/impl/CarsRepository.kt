package com.example.forcars.data.impl

import com.example.forcars.data.ResultType
import com.example.forcars.data.ws.CarsApi
import com.example.forcars.di.module.model.request.CarsRequest
import com.example.forcars.entity.Cars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarsRepository @Inject constructor(private val carsApi: CarsApi) {

    suspend fun getCars(): Flow<ResultType<List<Cars>>> = flow {
        try {
            val response = carsApi.getCars()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultType.Success(it.toCars()))
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

    suspend fun postCars(car: CarsRequest): Flow<ResultType<Boolean>> = flow {
        val response = carsApi.postCars(car)
        if (response.isSuccessful) {
            emit(ResultType.Success(true))
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Error en la petición"
            emit(ResultType.Error(errorMsg))
        }
    }.catch { exception ->
        // Manejar la excepción aquí
        val errorMessage = "Error al subir el anuncio: ${exception.message}"
        emit(ResultType.Error(errorMessage))
    }
}