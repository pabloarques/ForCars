package com.example.forcars.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forcars.common.ResultType
import com.example.forcars.domain.impl.GetCarUseCaseImpl
import com.example.forcars.entity.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCarUseCaseImpl: GetCarUseCaseImpl) : ViewModel() {

    private val _cars = MutableStateFlow<List<Cars>>(emptyList())
    val cars: StateFlow<List<Cars>> = _cars

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun getCars() {
        viewModelScope.launch {
            getCarUseCaseImpl.invoke()
                .collect() {
                    when (it) {
                        is ResultType.Success -> {
                            _cars.value = it.data
                        }

                        is ResultType.Error -> {
                            _error.value = it.message.toString()
                        }
                    }
                }
        }
    }
}