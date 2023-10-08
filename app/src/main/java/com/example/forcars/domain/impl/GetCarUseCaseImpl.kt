package com.example.forcars.domain.impl

import com.example.forcars.common.ResultType
import com.example.forcars.data.repository.impl.CarsRepositoryImpl
import com.example.forcars.domain.GetCarUseCase
import com.example.forcars.entity.Cars
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarUseCaseImpl @Inject constructor(
    private val carsRepositoryImpl: CarsRepositoryImpl
) : GetCarUseCase {
    override suspend operator fun invoke(): Flow<ResultType<List<Cars>>> =
        carsRepositoryImpl.getCars()
}