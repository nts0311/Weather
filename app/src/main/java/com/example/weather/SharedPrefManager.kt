package com.example.weather

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefManager(private val sharedPreferences: SharedPreferences) {
    fun getLastLocationId() = sharedPreferences.getLong(Constants.LAST_LOACTION_ID_KEY, -1)

    fun setLastLocationId(value: Long) {
        sharedPreferences.edit {
            putLong(Constants.LAST_LOACTION_ID_KEY, value)
        }
    }

    fun getCurrentLocationId() = sharedPreferences.getLong(Constants.CURRENT_LOACTION_ID_KEY, -1)

    fun setCurrentLocationId(value: Long) {
        sharedPreferences.edit {
            putLong(Constants.CURRENT_LOACTION_ID_KEY, value)
        }
    }
}

