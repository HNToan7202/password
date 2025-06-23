package com.example.yourpass.domain.interactor.storagelist

import com.example.yourpass.data.entity.OperationResult
import com.example.yourpass.data.entity.StorageOption
import com.ivanovsky.passnotes.data.entity.FSType
import com.ivanovsky.passnotes.domain.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.invoke

class StorageListInteractor @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val fileSystemResolver: FileSystemResolver,

    ) {
    suspend fun getStorageOptions(): OperationResult<List<StorageOption>> =
        withContext(dispatchers.IO) {
            val availableFsTypes = fileSystemResolver.getAvailableFsTypes()

            val options = mutableListOf<StorageOption>()

            for (fsType in SORTED_FS_TYPES) {
                if (fsType !in availableFsTypes) {
                    continue
                }

                val optionFactory = optionFactories[fsType] ?: continue

                val option = withContext(dispatchers.IO) {
                    optionFactory.invoke()
                } ?: continue

                options.add(option)
            }

            OperationResult.success(options)
        }

    companion object {
        private val SORTED_FS_TYPES = listOf(
            FSType.INTERNAL_STORAGE,
            FSType.SAF,
            FSType.EXTERNAL_STORAGE,
            FSType.FAKE
        )
    }


}