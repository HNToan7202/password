package com.example.yourpass.presentation.home

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.keemobile.kotpass.models.Entry
import com.example.yourpass.R
import com.example.yourpass.presentation.base.BaseScreen
import com.example.yourpass.presentation.main.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController?,
    viewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {

    val entries = viewModel.entries.collectAsState().value

    BaseScreen(
        uiState = viewModel.uiState.value,
        onErrorDismiss = {},
        onSuccessDismiss = {},
        content = {
            EntryListScreen(entries)
        }
    )

}

@Composable
fun EntryListScreen(entries: List<Entry>) {

    LazyColumn {
        items(entries.size) { i ->
            EntryListItem(entry = entries[i], onItemClick = {

            })
        }
    }
}

@Composable
fun EntryListItem(entry: Entry, onItemClick: (Entry) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onItemClick(entry) } // Make the entire row clickable
    ) {
        // Icon with a circular blue background
        Box(
            modifier = Modifier
                .size(40.dp) // Set the size of the background
                .clip(CircleShape) // Clip the background to a circle
                .background(Color.Blue), // Set the background color to blue
            contentAlignment = Alignment.Center // Align the icon in the center
        ) {
            Icon(
                painter = painterResource(R.drawable.key_icon),
                contentDescription = "Entry Icon",
                modifier = Modifier.size(24.dp), // Set the size of the icon
                tint = Color.White // Set the icon color to white
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f) // Let this Column take up remaining space
        ) {
            Text(text = " ${entry.fields.userName?.content ?: "No Username"}")
            if (entry.fields.password != null) {
                Text(text = "Password: ${entry.fields.password?.content ?: "No Password"}")
            }
        }

        // Next icon button
        IconButton(
            modifier = Modifier
                .size(24.dp)
                .clickable { }, // Handle click event for the Next icon
            onClick = { /* Handle click here, if needed */ }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward, // Default "Next" icon from Icons.Filled
                contentDescription = "Next",
                tint = Color.Gray // Adjust the tint of the icon as needed
            )
        }
    }
}