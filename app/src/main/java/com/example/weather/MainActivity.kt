package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.example.weather.database.room_entities.LocationEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val l = LocationEntity(0.0,0.0,"")
        viewModel.insertLocation(l)
        l.dbId = 1
        viewModel.getWeatherInfoOfLocation(l).asLiveData()
            .observe(this)
            {
                if(it==null) return@observe


            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LocationTracker.REQUEST_LOCATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == 0 }) {
                    LocationTracker.getCurrentLocation(this){

                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Location permissions not granted!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}