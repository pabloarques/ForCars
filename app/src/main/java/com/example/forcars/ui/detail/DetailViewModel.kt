package com.example.forcars.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forcars.entity.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class DetailViewModel @Inject constructor() : ViewModel() {

    private val _cars = MutableLiveData<Cars>()
    val cars: LiveData<Cars> get() = _cars

}