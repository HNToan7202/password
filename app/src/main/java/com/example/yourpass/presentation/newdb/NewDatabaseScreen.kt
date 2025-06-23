package com.example.yourpass.presentation.newdb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourpass.R
import com.example.yourpass.Screen
import com.example.yourpass.presentation.main.MainViewModel


@Composable
fun NewDatabaseScreen(
    navController: NavController?, viewModel: NewDatabaseViewModel, mainViewModel: MainViewModel?
) {
    
    val context = LocalContext.current
    val savedStateHandle = navController?.currentBackStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        viewModel.handleSavedState(savedStateHandle)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column {
            Text(text = "Storage type: Not type")

            when (viewModel.result) {
                0 -> {
                    viewModel.updatePath(context)
                    Text("Path: ${viewModel.path}")
                }
            }

            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    navController?.navigate(Screen.StorageListScreen.route)
                },
            ) {
                Text("Thay đổi vị trí")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add entry templates checkbox
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.isAddTemplatesChecked,
                onCheckedChange = { viewModel.updateTemplatesChecked(it) }
            )
            Text(text = "Add entry templates", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // File Name input
        OutlinedTextField(
            value = viewModel.fileName,
            onValueChange = { viewModel.updateFileName(it) },
            label = { Text("File name") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Text(".kdbx", style = MaterialTheme.typography.bodyMedium)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* Show/Hide password logic */ }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_eye_open),
                        contentDescription = "Visibility Icon"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password input
        OutlinedTextField(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            label = { Text("Confirm") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* Show/Hide password logic */ }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_eye_open),
                        contentDescription = "Visibility Icon"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = { /* Submit logic */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@Preview
@Composable
fun NewDatabaseScreenPreview() {
    NewDatabaseScreen(navController = null, mainViewModel = null, viewModel = hiltViewModel())
}