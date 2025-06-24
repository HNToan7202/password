package com.example.yourpass

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yourpass.Screen.StorageListScreen
import com.example.yourpass.presentation.home.HomeScreen
import com.example.yourpass.presentation.main.MainState
import com.example.yourpass.presentation.main.MainViewModel
import com.example.yourpass.presentation.newdb.NewDatabaseScreen
import com.example.yourpass.presentation.storage.SelectStorageScreen

sealed class Screen(val route: String) {
    object NewDatabaseScreen : Screen("NewDatabaseScreen")
    object StorageListScreen : Screen("StorageListScreen")
    object HomeScreen : Screen("Home")
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()

    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error != null && mainState.value.error != "") {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_LONG).show()
            mainViewModel.setError("")
        }
    }
    NavHost(navController = navController, startDestination = Screen.NewDatabaseScreen.route) {
        composable(Screen.NewDatabaseScreen.route) {
            NewDatabaseScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                mainViewModel = mainViewModel
            )
        }

        composable(Screen.StorageListScreen.route) {
            SelectStorageScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                mainViewModel = mainViewModel
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                mainViewModel = mainViewModel
            )
        }
    }
}