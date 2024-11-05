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
import com.example.firebaseauth.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseauth.AuthViewModel

@Composable
fun Account(modifier: Modifier = Modifier, authViewModel: AuthViewModel = viewModel() ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5F8C60))
            .padding(horizontal = 10.dp, vertical = 10.dp) // Add padding to prevent overlap with bottom navigation
            .navigationBarsPadding(), // Adds padding to avoid overlap with system navigation bars
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top row with account icon, title, and edit icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Account icon
            IconButton(onClick = { /* Handle account action */ }) {
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

        Spacer(modifier = Modifier.height(12.dp))

        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.img_1), // Replace with your profile picture resource
            contentDescription = "Profile Picture",
            modifier = Modifier.size(100.dp) // Adjust size as needed
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ID Number
        UserProfileInfo(label = "ID Number:", value = "764539")

        // User profile information
        UserProfileInfo(label = "Name:", value = "John Doe")
        UserProfileInfo(label = "Address:", value = "456 Elm St, Cityville")
        UserProfileInfo(label = "Contact No:", value = "+9876543210")
        UserProfileInfo(label = "Age:", value = "32")
        UserProfileInfo(label = "Date of Birth:", value = "February 2, 1992")
        UserProfileInfo(label = "Company Name:", value = "Example Corp")
        UserProfileInfo(label = "Position:", value = "Developer")
        UserProfileInfo(label = "Department:", value = "Development")
        UserProfileInfo(label = "Emergency Contact:", value = "+01234")

        Spacer(modifier = Modifier.height(2.dp))



            // Log out button
        Button(
            onClick = { authViewModel.signout() }, // Use instance to call signout()
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Log Out")
        }
    }
}
@Composable
fun UserProfileInfo(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
    }
}