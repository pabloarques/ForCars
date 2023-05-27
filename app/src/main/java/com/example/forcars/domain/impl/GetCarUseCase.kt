package com.example.forcars.domain.impl

import com.example.forcars.data.ResultType
import com.example.forcars.data.impl.CarsRepository
import com.example.forcars.entity.Cars
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarUseCase @Inject constructor(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(): Flow<ResultType<List<Cars>>> = carsRepository.getCars()
}