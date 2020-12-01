package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.*

class LocationTracker {
    companion object {
        fun getCurrentLocation(context: Context, locationListener: (Location) -> Any): Boolean {
            if (!isLocationEnabled(context)) {
                return false
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                (context as Activity).requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), REQUEST_LOCATION_PERMISSION_CODE
                )
                return false
            } else {
                requestLocationUpdate(context, locationListener)
                return true
            }
        }

        @SuppressLint("MissingPermission")
        private fun requestLocationUpdate(context: Context, locationListener: (Location) -> Any) {
            val locationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)

            val locationRequest = LocationRequest.create().apply {
                interval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationRequest ?: return
                    locationListener(locationResult!!.locations.first())
                    locationClient.removeLocationUpdates(this)
                }
            }

            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                context.mainLooper
            )
        }

        fun isLocationEnabled(context: Context): Boolean {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return LocationManagerCompat.isLocationEnabled(locationManager)
        }

        const val REQUEST_LOCATION_PERMISSION_CODE = 1
    }
}