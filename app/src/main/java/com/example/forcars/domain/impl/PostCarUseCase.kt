package com.example.forcars.domain.impl

import com.example.forcars.common.ResultType
import com.example.forcars.data.impl.CarsRepository
import com.example.forcars.di.module.model.request.CarsRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCarUseCase @Inject constructor(private val carsRepository: CarsRepository) {

    suspend fun execute(car: CarsRequest): Flow<ResultType<Boolean>> {
        return carsRepository.postCars(car)
    }
}