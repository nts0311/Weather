package com.example.weather

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.database.room_entities.LocationEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.parseColor("#644bde"),
                Color.parseColor("#4bcade")
            )
        )


        gradientDrawable.cornerRadius = 0f;


        //Set Gradient
        root_layout.setBackground(gradientDrawable);

        viewModel.currentLocationId = sharedPrefManager.getCurrentLocationId()
        registerObservers()
        refresh()
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
                    refresh()
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

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork ?: return false

        val nc = cm.getNetworkCapabilities(activeNetwork) ?: return false

        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun registerObservers() {
        viewModel.selectedLocation.observe(this)
        {
            if (it == null) return@observe

            viewModel.getData(it)
        }

        viewModel.weatherInfo.observe(this)
        {
            Log.d("aaa", it.toString())
        }

    }

    private fun refresh()
    {
        val hasGps = LocationTracker.isLocationEnabled(this)
        if (hasGps) {
            LocationTracker.getCurrentLocation(this)
            {
                val currentLocation = LocationEntity(it.latitude, it.longitude)
                if (viewModel.currentLocationId != -1L)
                    currentLocation.dbId = viewModel.currentLocationId

                viewModel.updateAndSetCurrentLocation(currentLocation)
            }
        } else {
            if(viewModel.currentLocationId == -1L)
                Toast.makeText(this, "Cannot load weather data: No GPS", Toast.LENGTH_SHORT).show()
            else
                viewModel.setLocation(viewModel.currentLocationId)
        }
    }



}