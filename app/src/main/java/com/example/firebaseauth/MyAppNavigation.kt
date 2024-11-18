package com.example.firebaseauth

import AuthViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.pages.HomePage
import com.example.firebaseauth.pages.LoginPage
import com.example.firebaseauth.pages.SignupPage
import com.example.googlemappage.MapPage
import com.google.android.gms.maps.model.LatLng

// Define your screens
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Map : Screen("map")
}

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    currentLocation: LatLng?
) {
    val navController = rememberNavController()

    // Check the authentication state from your ViewModel
    val isAuthenticated = authViewModel.isAuthenticated

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Map.route else Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginPage(
                authViewModel = authViewModel,
                navController = navController,  // Correctly passing the navController
                onLoginSuccess = {
                    // Navigating to the Map screen on successful login
                    navController.navigate(Screen.Map.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Map.route) {
            // Display the Map page if authenticated
            MapPage(modifier = modifier, currentLocation = currentLocation)
        }
    }
}

