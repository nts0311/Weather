package com.example.weather

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.example.weather.database.AppDatabase
import com.example.weather.repositories.WeatherInfoRepository
import com.example.weather.worker.UpdateDataWorker
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class WorkerTest {
    private lateinit var context: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var appDb : AppDatabase

    @Inject
    lateinit var wRepo : WeatherInfoRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @Test
    fun testWorker() {

        val worker = TestListenableWorkerBuilder<UpdateDataWorker>(context).setWorkerFactory(workerFactory).build() as UpdateDataWorker

        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.failure()))
        }
    }

    @Test
    fun test1()
    {
        runBlocking {
            val curLoc = appDb.locationDao.getLocation(1L)



            val wInfoF = appDb.weatherInfoDao.getWeatherInfoByLocationFlow(1L).distinctUntilChanged()

            wInfoF.onEach {
                if (it ==null) return@onEach
                Log.d("aaa", it.dbId.toString())
            }
                .launchIn(this)


            wRepo.getWeatherData(curLoc)



            Log.d("aaa", "aaaaaa")
        }
    }
}