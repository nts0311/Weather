package com.example.weather

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.weather.worker.UpdateDataWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
            PeriodicWorkRequestBuilder<UpdateDataWorker>(3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(UpdateDataWorker.UNIQUE_WORK_NAME)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            UpdateDataWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            updateWeatherDataRequest
        )
    }
}