package com.example.yourpass.presentation.storage

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.yourpass.data.entity.FileDescriptor
import com.ivanovsky.passnotes.data.entity.FSType
import com.ivanovsky.passnotes.data.entity.FSType.EXTERNAL_STORAGE
import com.ivanovsky.passnotes.data.entity.FSType.INTERNAL_STORAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectStorageViewModel @Inject constructor() : ViewModel() {

    val selectedIndex = mutableIntStateOf(-1) // Trạng thái chỉ mục được chọn

    private var storageOptions: List<StorageOption>? = null

//    val storageOptions = listOf(
//        StorageOption("Private app storage"),
//        StorageOption("External storage (System File Picker)", requiresPermission = true),
//        StorageOption("External storage (Built-in file picker)", requiresPermission = true)
//    )

    init {
//        loadData()
    }

    // Hàm xử lý khi người dùng chọn một mục
    fun onItemClicked(index: Int) {
        selectedIndex.intValue = index
    }

    private fun onDeviceStorageSelected(root: FileDescriptor, type: FSType, action: Action) {
        if (action == Action.PICK_FILE) {
            //TODO
        } else if (action == Action.PICK_STORAGE) {
            when (type) {
                EXTERNAL_STORAGE -> {

                }

                INTERNAL_STORAGE -> {

                }

                else -> throw IllegalArgumentException()
            }
        }
    }
//
//    private fun loadData() {
//
//        viewModelScope.launch {
//            val getOptionsResult = interactor.getStorageOptions()
//            if (getOptionsResult.isSucceededOrDeferred) {
//                val options = getOptionsResult.obj
//                storageOptions = options
//
//                val cellModels = modelFactory.createCellModel(options)
//                setCellViewModels(viewModelFactory.createCellViewModels(cellModels, eventProvider))
//
//                setScreenState(ScreenState.data())
//            } else {
//                setErrorState(getOptionsResult.error)
//            }
//        }
//    }
//
//    private fun onStorageOptionClicked(fsType: FSType) {
//        val selectedOption = storageOptions?.find { fsType == it.root.fsAuthority.type } ?: return
//        this.selectedOption = selectedOption
//
//        when (selectedOption.root.fsAuthority.type) {
//            INTERNAL_STORAGE, E guygtftXTERNAL_STORAGE -> {
//                onDeviceStorageSelected(selectedOption.root, selectedOption.root.fsAuthority.type)
//            }
//
//            UNDEFINED -> {}
//        }
//    }

}