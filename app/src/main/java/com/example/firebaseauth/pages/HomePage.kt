package com.example.firebaseauth.pages

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthState
import com.example.firebaseauth.ui.theme.NavItem
import com.example.googlemappage.MapPage
import com.google.android.gms.maps.model.LatLng

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.observeAsState()
    var selectedIndex by remember { mutableStateOf(0) }
    var timeInput by remember { mutableStateOf("") }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    // Simulating fetching the current location (replace with actual location logic)
    LaunchedEffect(Unit) {
        currentLocation = LatLng(37.7749, -122.4194) // Example location: San Francisco
    }

    // Handle unauthenticated state
    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            Log.d("HomePage", "User is unauthenticated, navigating to login.")
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    // Navigation items for the bottom bar
    val navItemList = listOf(
        NavItem("Map", Icons.Default.LocationOn),
        NavItem("DTR", Icons.Default.DateRange),
        NavItem("Account", Icons.Default.AccountCircle)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label) },
                        label = { Text(text = navItem.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            onNavigateToCamera = { onBack ->
                selectedIndex = 3 // Navigate to CameraPage
                onBack(timeInput) // Pass time input back when done
            },
            onBack = { time ->
                timeInput = time // Capture returned time from CameraPage
                selectedIndex = 1 // Navigate back to DTR
            },
            authViewModel = authViewModel,
            navController = navController,
            currentLocation = currentLocation
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onNavigateToCamera: (onBack: (String) -> Unit) -> Unit,
    onBack: (String) -> Unit,
    authViewModel: AuthViewModel,
    navController: NavController,
    currentLocation: LatLng?
) {
    when (selectedIndex) {
        0 -> MapPage(modifier = modifier, currentLocation = currentLocation)
        1 -> DTR(onNavigateToCamera = onNavigateToCamera)
        2 -> Account(
            modifier = modifier,
            authViewModel = authViewModel,
            navController = navController
        )
        3 -> CameraPage(onBack = onBack)
    }
}
