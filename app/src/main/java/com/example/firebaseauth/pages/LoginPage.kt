package com.example.firebaseauth.pages

import AuthViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthState
//import com.example.firebaseauth.viewmodel.AuthViewModel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.style.TextAlign
//import com.example.firebaseauth.viewmodel.AuthViewModel


@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // State to toggle password visibility
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp) // Added more top padding to pull the content lower
            .padding(horizontal = 16.dp), // Add horizontal padding for input fields and buttons
        verticalArrangement = Arrangement.Top, // Align content towards the top of the screen
        horizontalAlignment = Alignment.CenterHorizontally // Align content horizontally to the center
    ) {
        // Logo at the top
        Image(
            painter = painterResource(id = com.example.firebaseauth.R.drawable.logo), // Replace with your logo resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp) // Adjust size as needed
                .padding(bottom = 16.dp) // Add some space below the logo
        )

        // "Login Page" Text with bold style, centered
        Text(
            text = "LOGIN FORM ",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, // This ensures that the text itself is centered in case it wraps
            modifier = Modifier.padding(bottom = 24.dp) // Added bottom padding to separate title from inputs
        )

        // Email Input with Icon
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email, // Email icon
                    contentDescription = "Email Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Space between email field and password field
        )

        // Password Input with Show/Hide Toggle
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp) // Space between password field and login button
        )

        // Login Button
        Button(
            onClick = { authViewModel.login(email, password) },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Space between login button and signup option
        ) {
            // Set the font size of the button text to make it bigger
            Text(
                text = "Login",
                fontSize = 22.sp, // Increased font size to 24.sp
                fontWeight = FontWeight.Bold // Optional: Make the text bold
            )
        }


        // "Don't have an account?" Text with Signup Button
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                fontSize = 20.sp // Adjust the font size as needed
            )

            TextButton(
                onClick = {
                    navController.navigate("signup")
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue) // Set color to blue
            ) {
                Text(
                    text = "Signup",
                    fontSize = 20.sp // Adjust the font size as needed
                )
            }
        }
    }
}

