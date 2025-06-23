package com.example.yourpass.data.repository

import android.content.Context
import com.example.yourpass.data.entity.FSAuthority
import com.example.yourpass.data.repository.file.FileSystemProvider
import com.example.yourpass.data.repository.file.RegularFileSystemProvider
import com.ivanovsky.passnotes.data.entity.FSType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

open class FileSystemResolver @Inject constructor(
    protected open val factories: Map<FSType, Factory>,
    private val context: Context
) {

    fun interface Factory {
        fun createProvider(fsAuthority: FSAuthority): FileSystemProvider
    }

    companion object {
        // Không cần truyền context trong buildFactories nữa
        fun buildFactories(
            isExternalStorageAccessEnabled: Boolean,
            context: Context,
        ): Map<FSType, Factory> {
            val result = mutableMapOf(
                // Để Hilt tự động inject các factory
                FSType.INTERNAL_STORAGE to InternalFileSystemFactory(context),
                // Các factory khác có thể được thêm vào tương tự
            )

//            if (isExternalStorageAccessEnabled) {
//                result[FSType.EXTERNAL_STORAGE] = ExternalFileSystemFactory()
//            }

            return result
        }
    }

    class ExternalFileSystemFactory(private val context: Context) : Factory {
        override fun createProvider(fsAuthority: FSAuthority): FileSystemProvider {
            return RegularFileSystemProvider(context, FSAuthority.EXTERNAL_FS_AUTHORITY)
        }
    }

    class InternalFileSystemFactory @Inject constructor(
        private val context: Context
    ) : Factory {
        override fun createProvider(fsAuthority: FSAuthority): FileSystemProvider {
            return RegularFileSystemProvider(context, FSAuthority.INTERNAL_FS_AUTHORITY)
        }
    }
}