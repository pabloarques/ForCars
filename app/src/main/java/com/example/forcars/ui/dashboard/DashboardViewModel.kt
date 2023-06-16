package com.example.forcars.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forcars.common.ResultType
import com.example.forcars.di.module.model.request.CarsRequest
import com.example.forcars.domain.impl.PostCarUseCase
import com.example.forcars.ui.common.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val postCarUseCase: PostCarUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun postCar(
        marca: String,
        modelo: String,
        combustible: String,
        cambio: String,
        cv: Int,
        year: Int,
        image: String,
        motor: String,
        ubicacion: String,
        price: Int,
        kilometros: Int,
        telefono: Int,
        correo: String
    ) {

        val car = CarsRequest(
            marca,
            modelo,
            combustible,
            cambio,
            cv,
            year,
            image,
            motor,
            ubicacion,
            price,
            kilometros,
            telefono,
            correo
        )

        viewModelScope.launch {
            when (val result = postCarUseCase.execute(car).first()) {
                is ResultType.Success -> {
                    _uiState.value = UiState.Success("El anuncio se ha subido correctamente")
                }

                is ResultType.Error -> {
                    _uiState.value = UiState.Error("Error al subir el anuncio: ${result.message}")
                }
            }
        }
    }

    fun validateInt(value: String): Int {
        return if (value.isBlank()) {
            0
        } else {
            value.toInt()
        }
    }
}