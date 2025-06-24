package com.example.yourpass.presentation.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(isLoading: Boolean) {
    if (isLoading) {
        @Composable
        fun LoadingDialog() {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Loading...") },
                text = { Text("Please wait...") },
                confirmButton = {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            )
        }
    }
}
