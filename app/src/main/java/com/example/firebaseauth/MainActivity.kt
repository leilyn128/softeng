package com.example.firebaseauth

import AuthViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.firebaseauth.ui.theme.FirebaseAuthTheme
//import com.example.googlemappage.LocationHelper
import com.example.googlemappage.MapPage
import com.google.android.gms.maps.model.LatLng
import com.example.firebaseauth.login.LocationHelper


class MainActivity : ComponentActivity() {
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable immersive edge-to-edge UI

        val authViewModel: AuthViewModel by viewModels() // ViewModel scoped to Activity lifecycle

        // MutableState to hold the user's current location
        val currentLocation = mutableStateOf<LatLng?>(null)

        // Initialize LocationHelper with context and location callback
        locationHelper = LocationHelper(this) { latLng ->
            currentLocation.value = latLng
        }

        // Start location updates
        locationHelper.startLocationUpdates(this)

        setContent {
            FirebaseAuthTheme {
                MainContent(
                    authViewModel = authViewModel,
                    currentLocation = currentLocation.value
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        locationHelper.stopLocationUpdates()
    }
}

@Composable
fun MainContent(
    authViewModel: AuthViewModel,
    currentLocation: LatLng?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        MyAppNavigation(
            modifier = Modifier.padding(innerPadding),
            authViewModel = authViewModel,
            currentLocation = currentLocation
        )
    }
}
