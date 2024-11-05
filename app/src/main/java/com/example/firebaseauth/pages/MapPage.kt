package com.example.firebaseauth.pages

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseauth.R

@Composable
fun MapPage(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(true) } // Set to true to show dialog on start

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5F8C60)) // Background color
    ) {
        // Background Image filling the bottom
        // for the user just change the geo
        Image(
            painter = painterResource(id = R.drawable.img_4), // Replace with your image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .size(500.dp) // Set the desired size for the image
                .align(Alignment.CenterStart) // Align the image to the bottom center
        )

        // Top layout with logo, title, and notification icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding around the row
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Space between elements
        ) {
            // Logo on the left
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your image resource
                contentDescription = "Top Left Logo",
                modifier = Modifier
                    .size(80.dp) // Set the desired size for the image
            )

            // Title in the center
            // for the user just change the text
            Text(
                text = "LOCATION",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.weight(1f), // Takes remaining space
                textAlign = androidx.compose.ui.text.style.TextAlign.Center // Center the title text
            )

            // Notification icon on the right
            IconButton(onClick = {
                showDialog = true // Show dialog when icon is clicked
            }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }

        // Popup dialog for notification
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Location Required") },
                text = { Text("Please turn on your location.") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
