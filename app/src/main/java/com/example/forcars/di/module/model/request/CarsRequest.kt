package com.example.forcars.di.module.model.request

import com.google.gson.annotations.SerializedName

data class CarsRequest(
    @SerializedName("marca") val marca: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("combustible") val combustible: String,
    @SerializedName("cambio") val cambio: String,
    @SerializedName("cv") val cv: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("image") val image: String,
    @SerializedName("motor") val motor: String,
    @SerializedName("ubicacion") val ubicacion: String,
    @SerializedName("price") val price: Int,
    @SerializedName("kilometros") val kilometros: Int,
    @SerializedName("telefono") val telefono: Int,
    @SerializedName("correo") val correo: String
)