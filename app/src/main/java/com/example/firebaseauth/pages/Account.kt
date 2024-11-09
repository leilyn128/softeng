package com.example.firebaseauth.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebaseauth.AuthState
import com.example.firebaseauth.R
import com.example.firebaseauth.AuthViewModel

@Composable
fun Account(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5F8C60))
            .padding(horizontal = 16.dp, vertical = 20.dp) // Adjusted padding
            .navigationBarsPadding(), // Add padding to avoid overlap with system navigation bars
        verticalArrangement = Arrangement.Top, // Align content towards the top
        horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
    ) {
        // Top row with account icon, title, and edit icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Account icon
            IconButton(onClick = { /* Handle account details action */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = Color.White
                )
            }

            // Title
            Text(
                text = "Profile Details",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            // Edit icon
            IconButton(onClick = { /* Handle edit action */ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Increased space after the top row

        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.img_1), // Replace with your profile picture resource
            contentDescription = "Profile Picture",
            modifier = Modifier.size(100.dp) // Adjust size as needed
        )

        Spacer(modifier = Modifier.height(12.dp)) // Space after the profile picture

        // User profile information rows
        UserProfileInfo(label = "ID Number:", value = "764539")
        UserProfileInfo(label = "Name:", value = "John Doe")
        UserProfileInfo(label = "Address:", value = "456 Elm St, Cityville")
        UserProfileInfo(label = "Contact No:", value = "+9876543210")
        UserProfileInfo(label = "Date of Birth:", value = "February 2, 1992")
        UserProfileInfo(label = "Company Name:", value = "Example Corp")
        UserProfileInfo(label = "Emergency Contact:", value = "+01234")

        Spacer(modifier = Modifier.height(24.dp)) // Extra spacing before Log Out button

        // Log out button
        Button(
            onClick = {
                authViewModel.signout() // Call signout method

                // Check if signout was successful and navigate
                if (authViewModel.authState.value is AuthState.Unauthenticated) {
                    // Navigate to login screen after logout
                    navController.navigate("login") {
                        // Clear back stack to ensure the user can't go back to the account screen
                        popUpTo("account") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth() // Make the button span the full width
                .padding(horizontal = 32.dp) // Add horizontal padding for the button
                .padding(bottom = 16.dp) // Space at the bottom of the button
        ) {
            Text(
                text = "Log Out",
                fontSize = 18.sp, // Reduced font size for button text
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun UserProfileInfo(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Added vertical padding between each info line
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
    }
}
//hurryyyyyyyyyyyyyyyyf
//hsbyuhcdh