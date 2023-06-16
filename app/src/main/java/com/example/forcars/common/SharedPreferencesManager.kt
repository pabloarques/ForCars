package com.example.forcars.common

import android.content.Context
import android.content.SharedPreferences
import com.example.forcars.ui.common.utils.Constants.KEY_IS_LOGGED_IN
import com.example.forcars.ui.common.utils.Constants.PREFS_NAME

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }
}