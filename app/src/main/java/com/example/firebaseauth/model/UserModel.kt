package com.example.firebaseauth.model

import com.google.firebase.Timestamp


data class UserModel(
    val employeeID: String,
    val username: String,
    val accountType: String,
    val address: String,
    val contactNumber: String,
    val dateOfBirth: Timestamp // Change to Timestamp
)

