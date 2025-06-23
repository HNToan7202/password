package com.example.yourpass.presentation.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourpass.data.entity.FileDescriptor
import com.example.yourpass.presentation.main.MainViewModel

@Composable
fun SelectStorageScreen(
    navController: NavController?, viewModel: SelectStorageViewModel, mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header for the screen
        Text(
            text = "Select storage", modifier = Modifier.padding(bottom = 16.dp)
        )

        // List of storage options
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(viewModel.storageOptions) { index, storageOption ->
                StorageOptionItem(
                    title = storageOption.title,
                    isSelected = viewModel.selectedIndex.intValue == index,
                    onClick = {
                        viewModel.onItemClicked(index)
                        if (index == 0) {

                            navController?.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("selectedIndex", 0)

                            navController?.popBackStack()  // Navigate back
                        }

                    },
                    requiresPermission = storageOption.requiresPermission
                )
            }
        }
    }
}

data class StorageOption(
    val root: FileDescriptor
)

@Composable
fun StorageOptionItem(
    title: String, isSelected: Boolean, onClick: () -> Unit, requiresPermission: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Handle click event
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background
            ) // Thay đổi màu nền khi được chọn
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title, modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check, // Hiển thị dấu check khi item được chọn
                    contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Hiển thị thông báo yêu cầu quyền nếu cần thiết
        if (requiresPermission) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "(Requires permission)",
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SelectStorageScreenPreview() {
    SelectStorageScreen(
        navController = null, viewModel = hiltViewModel(), mainViewModel = hiltViewModel()
    )
}