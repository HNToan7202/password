package com.example.yourpass.presentation.base

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        },
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        iconContentColor = MaterialTheme.colorScheme.onBackground,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
        tonalElevation = 6.dp,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Customize dialog properties
    )
}


@Preview
@Composable
fun PreviewAlertDialog() {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        CustomAlertDialog(
            onDismissRequest = { openDialog = false },
            onConfirm = { openDialog = false },
            onDismiss = { openDialog = false },
            title = "Confirmation",
            text = "Are you sure you want to proceed?"
        )
    }
}