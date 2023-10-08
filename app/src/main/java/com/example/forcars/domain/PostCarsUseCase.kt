package com.example.forcars.domain

import com.example.forcars.common.ResultType
import com.example.forcars.data.ws.model.request.CarsRequest
import kotlinx.coroutines.flow.Flow

interface PostCarsUseCase {
    suspend fun execute(car: CarsRequest): Flow<ResultType<Boolean>>
}