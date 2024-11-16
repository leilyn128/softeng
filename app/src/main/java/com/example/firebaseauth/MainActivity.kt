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
import androidx.compose.ui.Modifier
import com.example.firebaseauth.ui.theme.FirebaseAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable immersive edge-to-edge UI
        val authViewModel: AuthViewModel by viewModels() // ViewModel scoped to Activity lifecycle

        setContent {
            FirebaseAuthTheme {
                MainContent(authViewModel = authViewModel)
            }
        }
    }
}

@Composable
fun MainContent(authViewModel: AuthViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        MyAppNavigation(
            modifier = Modifier.padding(innerPadding),
            authViewModel = authViewModel
        )
    }
}

