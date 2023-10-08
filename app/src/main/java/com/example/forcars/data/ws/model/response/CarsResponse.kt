package com.example.forcars.data.ws.model.response

import com.example.forcars.entity.Cars
import com.google.gson.annotations.SerializedName

data class CarsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("items") val items: List<Cars>
) {
    fun toCars(): List<Cars> = items
}