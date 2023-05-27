package com.example.forcars.data.ws

import com.example.forcars.di.module.model.response.CarsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CarsApi {
    @GET("/api/collections/cars/records")
    suspend fun getCars(): Call<Response<CarsResponse>>
}