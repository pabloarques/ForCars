package com.example.forcars.entity

data class Cars(
    val id: String,
    val marca: String,
    val modelo: String,
    val combustible: String,
    val kilometros: Int,
    val cv: Int,
    val price: Int,
    val cambio: String,
    val year: Int,
    val motor: String,
    val image: String,
    val ubicacion: String
)