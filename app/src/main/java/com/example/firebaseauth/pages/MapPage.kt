package com.example.googlemappage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MarkerState
import com.example.firebaseauth.R

@Composable
fun MapPage(modifier: Modifier = Modifier, currentLocation: LatLng?) {
    var showDialog by remember { mutableStateOf(false) }

    // Using Scaffold to handle layout and bottom navigation space
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFF5F8C60), // Set the background color here
        topBar = {
            // Top Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Top Left Logo",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "LOCATION",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
            }
        },
        content = { innerPadding ->
            // Google Map Section with padding to avoid overlap with bottom navigation
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize() // Ensure Column takes up full screen
                .background(Color(0xFF5F8C60)) // Background color applied here
            ) {
                if (currentLocation != null) {
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 56.dp),  // Adjust for bottom navigation height
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = currentLocation),
                            title = "Current Location"
                        )
                    }
                } else {
                    Text(
                        text = "Fetching location...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    )

    // Notification Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Location Required") },
            text = { Text("Please turn on your location.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
