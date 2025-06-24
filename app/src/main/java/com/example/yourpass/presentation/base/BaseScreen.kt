package com.example.yourpass.presentation.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    uiState: UIState,
    onSuccessDismiss: () -> Unit,
    onErrorDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Content Screen
        content()

        when (uiState.state) {
            UIStateType.LOADING -> LoadingDialog()
            UIStateType.ERROR -> ErrorDialog(
                message = uiState.errorMessage ?: "An error occurred",
                onDismiss = onErrorDismiss
            )

            UIStateType.SUCCESS -> SuccessDialog(onDismiss = onSuccessDismiss)
            UIStateType.IDLE -> {}
        }
    }
}

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

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


@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Success") },
        text = { Text("Action completed successfully.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}