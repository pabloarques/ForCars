package com.example.forcars.data.repository

import com.example.forcars.common.ResultType
import com.example.forcars.entity.Cars
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCars(): Flow<ResultType<List<Cars>>>
}