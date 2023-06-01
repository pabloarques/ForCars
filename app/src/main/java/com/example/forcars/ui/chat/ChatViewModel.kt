package com.example.forcars.ui.chat

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    fun isValidEmail(email: String): Boolean {
        val pattern =
            Pattern.compile("([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)@(gmail\\.com|outlook\\.com|hotmail\\.com)")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}