package com.example.firebaseauth.login

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationServices


class LocationHelper(
    private val context: Context,
    private val onLocationUpdate: (LatLng) -> Unit
) {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // LocationRequest initialization
    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 5000L
    ).setMinUpdateIntervalMillis(2000L)  // Set the update frequency to every 2 seconds
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)  // Use high accuracy for best location updates
        .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location? = locationResult.lastLocation
            location?.let {
                val latLng = LatLng(it.latitude, it.longitude)
                Log.d("LocationHelper", "Updated Location: $latLng")
                onLocationUpdate(latLng)
            }
        }
    }

    // Starts location updates, handles permission request
    fun startLocationUpdates(activity: ComponentActivity) {
        val permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                requestLocationUpdates()
            } else {
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        // Check for location permission, request if necessary
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            requestLocationUpdates()
        }
    }

    // Requests location updates after permission is granted
    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, null
            )
        }
    }

    // Stops location updates when no longer needed
    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}