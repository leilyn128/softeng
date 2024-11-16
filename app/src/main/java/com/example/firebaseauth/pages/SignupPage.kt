package com.example.firebaseauth.pages

import AuthViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.foundation.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.app.DatePickerDialog
import android.util.Log
//import androidx.compose.foundation.DropdownMenu
//import androidx.compose.foundation.DropdownMenuItem
import androidx.compose.foundation.clickable
import java.util.*

@Composable

fun SignupPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    // State variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var employeeId by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contactNo by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }  // Date of birth
    var gender by remember { mutableStateOf("") }  // Gender
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current

    // DatePickerDialog state
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                dob = "$dayOfMonth/${month + 1}/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo at the top
        Image(
            painter = painterResource(id = com.example.firebaseauth.R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        // "Sign Up Form" Text with bold style, centered
        Text(
            text = "SIGN UP FORM",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Employee ID
        OutlinedTextField(
            value = employeeId,
            onValueChange = { employeeId = it },
            label = { Text(text = "Employee ID") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Employee ID Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Full Name
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "UserName") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Full Name Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Address
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = "Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Address Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Contact Number
        OutlinedTextField(
            value = contactNo,
            onValueChange = { contactNo = it },
            label = { Text(text = "Contact Number") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Contact Number Icon"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Date of Birth Input - DatePicker
        // Date of Birth Input - DatePicker
        OutlinedTextField(
            value = dob,
            onValueChange = {},
            label = { Text(text = "Date of Birth") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Date of Birth Icon"
                )
            },
            readOnly = true, // Keep it read-only so the user cannot type manually
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .clickable {
                    // Open the DatePickerDialog on click
                    Log.d("DateField", "Date field clicked, showing dialog")
                    datePickerDialog.show()
                }
        )


        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Password with Show/Hide Toggle
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
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        )

        // Confirm Password with Show/Hide Toggle
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirm Password") },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 15.dp)
        )

        // Sign Up Button
        Button(
            onClick = {
                if (password == confirmPassword) {
                    // Firebase registration logic here

                    // After successful registration, navigate to Home Page
                    navController.navigate("home") // Assuming "home" is your homepage route
                } else {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Sign Up",
                fontSize = 20.sp
            )
        }

        // Already have an account? Login Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = 20.sp
            )

            TextButton(
                onClick = {
                    navController.navigate("login")
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue)
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp
                )
            }
        }
    }
}
