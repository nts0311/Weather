package com.example.weather

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.example.weather.di.AppModule
import com.example.weather.di.DatabaseModule
import com.example.weather.di.NetworkModule
import com.example.weather.repositories.LocationRepository
import com.example.weather.repositories.WeatherInfoRepository
import com.example.weather.worker.FetchDataWorker
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
class WorkerTest {
    private lateinit var context: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @Test
    fun testWorker() {

        val worker = TestListenableWorkerBuilder<FetchDataWorker>(context).setWorkerFactory(workerFactory).build() as FetchDataWorker

        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.failure()))
        }
    }
}