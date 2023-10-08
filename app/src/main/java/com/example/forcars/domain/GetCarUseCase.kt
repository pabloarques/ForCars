package com.example.forcars.domain

import com.example.forcars.common.ResultType
import com.example.forcars.entity.Cars
import kotlinx.coroutines.flow.Flow

interface GetCarUseCase {
    suspend operator fun invoke(): Flow<ResultType<List<Cars>>>
}