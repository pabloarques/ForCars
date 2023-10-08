package com.example.forcars.domain.impl

import com.example.forcars.common.ResultType
import com.example.forcars.data.repository.impl.CarsRepositoryImpl
import com.example.forcars.data.ws.model.request.CarsRequest
import com.example.forcars.domain.PostCarsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCarUseCaseImpl @Inject constructor(
    private val carsRepositoryImpl: CarsRepositoryImpl
) : PostCarsUseCase {
    override suspend fun execute(car: CarsRequest): Flow<ResultType<Boolean>> =
        carsRepositoryImpl.postCars(car)
}