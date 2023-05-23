package com.example.forcars.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forcars.data.ResultType
import com.example.forcars.domain.impl.GetCarUseCase
import com.example.forcars.entity.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCarUseCase: GetCarUseCase) : ViewModel() {

    private val _cars = MutableLiveData<List<Cars>>()
    val cars: MutableLiveData<List<Cars>> get() = _cars

    fun getCars() {
        viewModelScope.launch {
            getCarUseCase.invoke()
                .collect() {
                    when (it) {
                        is ResultType.Success -> {
                            _cars.value = it.data.items
                        }

                        is ResultType.Error -> {
                            val msg = it.message
                        }
                    }
                }
        }
    }
}