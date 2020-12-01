package com.example.weather

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.weather.worker.FetchDataWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    private fun scheduleUpdateWeatherDataWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateWeatherDataRequest =
            PeriodicWorkRequestBuilder<FetchDataWorker>(3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(FetchDataWorker.UNIQUE_WORK_NAME)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            FetchDataWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateWeatherDataRequest
        )
    }
}