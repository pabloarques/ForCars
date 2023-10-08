package com.example.forcars.data.ws

import com.example.forcars.data.ws.model.request.CarsRequest
import com.example.forcars.data.ws.model.response.CarsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CarsApi {
    @GET("/api/collections/cars/records")
    suspend fun getCars(): Response<CarsResponse>

    @POST("/api/collections/cars/records")
    suspend fun postCars(@Body car: CarsRequest): Response<CarsResponse>

}