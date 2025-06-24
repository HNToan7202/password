package com.example.yourpass.presentation.newdb

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yourpass.Screen
import com.example.yourpass.data.entity.FileDescriptor
import com.example.yourpass.presentation.base.UIState
import com.example.yourpass.presentation.base.UIStateType
import com.example.yourpass.utils.FileUtils
import com.example.yourpass.utils.FileUtils.createPath
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.yourpass.presentation.base.BaseViewModel

@HiltViewModel
class NewDatabaseViewModel @Inject constructor(
) : BaseViewModel() {
    var fileName by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isAddTemplatesChecked by mutableStateOf(true)
    var path by mutableStateOf("")
    var result by mutableIntStateOf(-1)

    private var selectedStorage: SelectedStorage? = null

    private val savedStateHandle: SavedStateHandle? = null

    // Logic
    fun updatePath(context: Context) {
        path = context.filesDir.absolutePath
    }

    fun onResultChanged(returnedValue: Int) {
        result = returnedValue
    }

    fun updateFileName(name: String) {
        fileName = name
    }

    fun updatePassword(passwordValue: String) {
        password = passwordValue
    }

    fun updateConfirmPassword(passwordValue: String) {
        confirmPassword = passwordValue
    }

    fun updateTemplatesChecked(isChecked: Boolean) {
        isAddTemplatesChecked = isChecked
    }

    fun handleSavedState(savedStateHandle: SavedStateHandle?) {
        val returnedValue = savedStateHandle?.remove<Int>("selectedIndex")
        returnedValue?.let {
            result = it
        }
    }

    fun newResultKey(): String {
        return Screen.StorageListScreen.route + "_result_" + UUID.randomUUID()
    }


    fun createDbFile(): FileDescriptor {
        val selectedStorage = selectedStorage ?: throw IllegalStateException()

        return when (selectedStorage) {
            is SelectedStorage.ParentDir -> {
                val name = this.fileName
                val path = createPath(
                    parentPath = path,
                    name = "$name.kdbx"
                )

                FileDescriptor(
                    fsAuthority = selectedStorage.dir.fsAuthority,
                    path = path,
                    uid = path,
                    name = FileUtils.getFileNameFromPath(path),
                    isDirectory = false,
                    isRoot = false,
                    modified = null
                )
            }

            is SelectedStorage.File -> {
                selectedStorage.file
            }
        }
    }

    private sealed interface SelectedStorage {
        data class ParentDir(val dir: FileDescriptor) : SelectedStorage
        data class File(val file: FileDescriptor) : SelectedStorage
    }
}