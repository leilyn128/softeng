package com.example.firebaseauth.pages

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
import com.example.firebaseauth.AuthState
import com.example.firebaseauth.AuthViewModel
import com.example.firebaseauth.ui.theme.NavItem

//import com.example.firebaseauth.NavItem

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController,authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()
    var selectedIndex by remember { mutableStateOf(0) }
    var timeInput by remember { mutableStateOf("") } // To hold the time from CameraPage
    var isLoggedOut by remember {  mutableStateOf(false) }

    val navItemList = listOf(
        NavItem("Map", Icons.Default.LocationOn),
        NavItem("DTR", Icons.Default.DateRange),
        NavItem("Account", Icons.Default.AccountCircle)
    )

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit

        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!isLoggedOut )
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
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
           // isLoggedOut= isLoggedOut,
            //onLogout={ isLoggedOut=true},
            onNavigateToCamera = { onBack ->
                selectedIndex = 3 // Set to 3 to navigate to CameraPage
                onBack(timeInput) // Capture the time input when navigating back
                                 // isLoggedOut= isLoggedOut

            },
            onBack = { time ->
                timeInput = time // Store the time from CameraPage
                selectedIndex = 1 // Go back to DTR page
            }
        )
    }
}



@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    //isLoggedOut: Boolean,
    //onLogout: () -> Unit,
    onNavigateToCamera: (onBack: (String) -> Unit) -> Unit,
    onBack: (String) -> Unit,
    authViewModel: AuthViewModel = viewModel() // Obtain ViewModel instance by default
) {
   // if (isLoggedOut) {
        //LoginPage(modifier = modifier, authViewModel = authViewModel)
    //} else {
        when (selectedIndex) {
            0 -> MapPage(modifier = modifier) // Ensure MapPage accepts this modifier
            1 -> DTR(onNavigateToCamera = onNavigateToCamera) // Pass the callback
            2 -> Account(modifier = modifier, authViewModel = authViewModel)
            3 -> CameraPage(onBack = onBack) // Pass back navigation
        }
    }


    /*Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text ="Home Page", fontSize = 32.sp)

        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text ="Sign out")*/


   /* val navItemList = listOf(
        NavItem("Map", Icons.Default.LocationOn),
        NavItem("DTR", Icons.Default.DateRange),
        NavItem("Account", Icons.Default.AccountCircle)
    )*/





