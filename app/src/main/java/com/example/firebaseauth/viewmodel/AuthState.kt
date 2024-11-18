package com.example.firebaseauth.viewmodel



import com.google.firebase.auth.FirebaseUser

// Sealed class to represent different authentication states
sealed class AuthState {
    data class Authenticated(val user: FirebaseUser) : AuthState()
    sealed class AuthResult {
        // Success: Store the FirebaseUser object on successful authentication
        data class Success(val user: FirebaseUser) : AuthResult()

        // Failure: Indicate that the operation failed
        data class Failure(val message: String?) : AuthResult()

        // LoggedOut: Indicates the user has logged out
        object LoggedOut : AuthResult()
    }


    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
