package com.example.firebaseauth.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.AuthState
import com.example.firebaseauth.AuthViewModel
import com.example.firebaseauth.ui.theme.NavItem

//import com.example.firebaseauth.NavItem

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.observeAsState() // Observing authentication state
    var selectedIndex by remember { mutableStateOf(0) }
    var timeInput by remember { mutableStateOf("") }

    // Navigate to the login screen if the user is unauthenticated
    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            Log.d("HomePage", "User is unauthenticated, navigating to login.")
            navController.navigate("login") {
                popUpTo("home") { inclusive = true } // Clear back stack
                launchSingleTop = true // Avoid stacking multiple login screens
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
        modifier = Modifier.fillMaxSize(),
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
        // Screen content based on the selected tab
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
            authViewModel = authViewModel, // Pass authViewModel for Account page
            navController = navController // Pass navController for Account page
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
    navController: NavController
) {
    when (selectedIndex) {
        0 -> MapPage(modifier = modifier)
        1 -> DTR(onNavigateToCamera = onNavigateToCamera)
        2 -> Account(
            modifier = modifier,
            authViewModel = authViewModel,
            navController = navController
        )
        3 -> CameraPage(onBack = onBack)
    }
}


