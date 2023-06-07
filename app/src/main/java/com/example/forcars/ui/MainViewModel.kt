package com.example.forcars.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forcars.data.SharedPreferencesManager
import com.example.forcars.ui.common.utils.Event
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) :
    ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    private val _startActivityEvent = MutableLiveData<Event<Intent>>()
    val startActivityEvent: LiveData<Event<Intent>> get() = _startActivityEvent

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        _user.postValue(currentUser)

        val isLoggedIn = currentUser != null
        sharedPreferencesManager.setLoggedIn(isLoggedIn)
    }

    fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        _startActivityEvent.value = Event(signInIntent)
    }

    fun handleSignInResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            _user.value = user

            data.toString()
            sharedPreferencesManager.setLoggedIn(true)
        } else {
            // Manejar el error o cancelación de inicio de sesión
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferencesManager.isLoggedIn()
    }
}