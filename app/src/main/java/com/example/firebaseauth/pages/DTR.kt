package com.example.firebaseauth.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseauth.R

@Composable
fun DTR(
    modifier: Modifier = Modifier,
    onNavigateToCamera: (onBack: (String) -> Unit) -> Unit // Accept the navigation function
) {
    var showDialog by remember { mutableStateOf(false) } // State to control dialog visibility

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5F8C60))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Top Row for Logo, Title, and Icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Image in the top left corner
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your image resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            // Centered Title
            Text(
                text = "Daily Time Record",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Icons on the right
            Row {
                IconButton(onClick = { /* Handle notifications action */ }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                }

                IconButton(onClick = {
                    // Navigate to the camera when the add icon is clicked
                    onNavigateToCamera { message ->
                        // Handle back navigation if needed
                    }
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }

                IconButton(onClick = {
                    showDialog = true // Show dialog when MoreVert is clicked
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Make LazyColumn fill available space
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp) // Add some padding for better spacing
        ) {
            items(createTableData()) { row ->
                TableRow(row) { employeeName ->
                    // Handle the button click
                    onNavigateToCamera { message -> /* Implement navigation logic here */ }
                }
            }
        }

        // Popup dialog for notification
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                text = { Text("Print DTR.") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun TableRow(row: List<String>, onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        row.forEachIndexed { index, entry ->
            if (index == 1 && entry == "Open") { // Change "Open" to a button
                Button(
                    onClick = { onButtonClick(row[0]) }, // Pass the employee name (row[0])
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = entry) // Show "Open" as button text
                }
            } else {
                Text(
                    text = entry,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp), // Adjust padding as needed
                    fontSize = 18.sp,
                    fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal // Bold for the first column
                )
            }
        }
    }
}

// Function to create sample data for the table



// Function to create sample data for the table


// Function to create sample data for the table with two header rows
fun createTableData(): List<List<String>> {
    return listOf(
        listOf("Day", "AM ","PM"), // First header row
        listOf(       " ",       "  IN",  "   OUT",   "    IN", "    OUT"), // Second header row (can add descriptions if needed)
        listOf("1", "", "", "", ""), // First data row
        listOf("2", "7:55", "12:00", "1:00", "5:00"), // Second data row
        listOf("3", "7:30", "11:15", "12:30", "5:15"), // Third data row
        listOf("4", "7:50", "11:50", "12:40", "5:10"), // Fourth data row
        listOf("5", "8:10", "12:05", "1:10", "5:05"), // Fifth data row
        listOf("6", "7:45", "11:45", "12:55", "5:55"), // Sixth data row
        listOf("7", "", "", "", ""), // Seventh data row
        listOf("8", "", "", "", ""), // Eighth data row
        listOf("9", "8:05", "12:15", "", ""), // Ninth data row
        listOf("10", "", "", "", "")  // Tenth data row
    )
}



/*fun createTableData(): List<List<String>> {
    return listOf(
        listOf("Employees",       "            DTR"), // Header row
        listOf("John Doe", "Open"),
        listOf("Bob Smith", "Open"),
        listOf("Catherine Green", "Open"),
        listOf("David Lee", "Open"),
        listOf("Ella Brown", "Open"),
        listOf("Frank White", "Open"),
        listOf("Grace Black", "Open"),
        listOf("Hannah Clark", "Open"),
        listOf("Isaac Davis", "Open"),
        listOf("Jack Wilson", "Open")
    )
}*/