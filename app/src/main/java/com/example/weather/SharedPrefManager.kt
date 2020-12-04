package com.example.weather

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefManager(private val sharedPreferences: SharedPreferences) {
    fun getCurrentLocationId() = sharedPreferences.getLong(Constants.CURRENT_LOACTION_ID_KEY, -1)

    fun setCurrentLocationId(value: Long) {
        sharedPreferences.edit {
            putLong(Constants.CURRENT_LOACTION_ID_KEY, value)
        }
    }

    fun getLastUpdateTime() = sharedPreferences.getLong(Constants.LAST_UPDATE_TIME_KEY, -1)

    fun setLastUpdateTim(value: Long) {
        sharedPreferences.edit {
            putLong(Constants.CURRENT_LOACTION_ID_KEY, value)
        }
    }
}

