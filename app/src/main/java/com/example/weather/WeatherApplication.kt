package com.example.weather

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.weather.worker.FetchDataWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        //scheduleUpdateWeatherDataWork()
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

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