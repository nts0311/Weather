package com.example.weather

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weather.database.room_entities.LocationEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentLocationId = sharedPrefManager.getCurrentLocationId()
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

        }

        registerObservers()
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
                    LocationTracker.getCurrentLocation(this) {

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

    /*private fun refreshDataForSelectedLocation()
    {
        val hasNetwork = isNetworkAvailable()
        val hasGps = LocationTracker.isLocationEnabled(this)

        if(hasGps && hasNetwork)
        {
            if(viewModel.selectedLocationId == viewModel.currentLocationId)
            {
                val result = LocationTracker.getCurrentLocation(this)
                {
                    val location = LocationEntity(it.latitude, it.longitude)
                    location.dbId = viewModel.currentLocationId
                    viewModel.updateCurrentLocation(location)

                    viewModel.getData(viewModel.currentLocationId)
                }
            }
            else
            {
                viewModel.getData(viewModel.selectedLocationId)
            }
        }
        else if (!hasGps && hasNetwork)
        {

        }
        else if(hasGps && !hasNetwork)
        {

        }
        else
        {

        }


    }*/
}