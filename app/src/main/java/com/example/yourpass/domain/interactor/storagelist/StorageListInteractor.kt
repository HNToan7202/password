package com.example.yourpass.domain.interactor.storagelist


import com.example.yourpass.data.repository.FileSystemResolver
import com.ivanovsky.passnotes.data.entity.FSType
import com.ivanovsky.passnotes.domain.DispatcherProvider

class StorageListInteractor(
    private val dispatchers: DispatcherProvider,
    private val fileSystemResolver: FileSystemResolver,
    ) {

    companion object {
        private val SORTED_FS_TYPES = listOf(
            FSType.INTERNAL_STORAGE,
            FSType.SAF,
            FSType.EXTERNAL_STORAGE,
            FSType.FAKE
        )
    }

}