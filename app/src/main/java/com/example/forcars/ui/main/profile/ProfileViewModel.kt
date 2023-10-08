package com.example.forcars.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forcars.common.SharedPreferencesManager
import com.example.forcars.ui.common.utils.Event
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) :
    ViewModel() {

    private val _startMainActivityEvent = MutableLiveData<Event<Unit>>()
    val startMainActivityEvent: LiveData<Event<Unit>> get() = _startMainActivityEvent
    fun logout() {
        sharedPreferencesManager.setLoggedIn(false)
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        _startMainActivityEvent.value = Event(Unit)
    }
}