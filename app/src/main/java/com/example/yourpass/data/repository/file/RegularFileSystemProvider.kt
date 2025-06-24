package com.example.yourpass.data.repository.file


import android.content.Context
import android.os.Build
import android.os.Environment
import com.example.yourpass.data.entity.FSAuthority
import com.example.yourpass.data.entity.FileDescriptor
import com.example.yourpass.data.entity.OperationError.MESSAGE_FAILED_TO_ACCESS_TO_FILE
import com.example.yourpass.data.entity.OperationError.MESSAGE_WRITE_OPERATION_IS_NOT_SUPPORTED
import com.example.yourpass.data.entity.OperationError.newGenericIOError
import com.example.yourpass.data.entity.OperationError.newPermissionError
import com.example.yourpass.data.entity.OperationResult
import com.example.yourpass.domain.PermissionHelper
import com.example.yourpass.domain.Stacktrace
import com.example.yourpass.domain.entity.SystemPermission
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class RegularFileSystemProvider @Inject constructor (
    private val context: Context,
    private val fsAuthority: FSAuthority
) : FileSystemProvider {

    private val permissionHelper = PermissionHelper(context)
    val lock = Mutex()

    override suspend fun openFileForRead(
        file: FileDescriptor,
        onConflictStrategy: OnConflictStrategy,
        options: FSOptions
    ): OperationResult<InputStream> {
        val check = checkPermissionForPath(file.path)
        if (check.isFailed) {
            return check.takeError()
        }

        lock.lock()
        return try {
            val input = BufferedInputStream(FileInputStream(file.path))
            OperationResult.success(input)
        } catch (e: FileNotFoundException) {
            Timber.d(e)
            OperationResult.error(newGenericIOError(e.message, e))
        } catch (e: Exception) {
            Timber.d(e)
            OperationResult.error(newGenericIOError(e.message, e))
        } finally {
            lock.unlock()
        }
    }

    override suspend fun openFileForWrite(
        file: FileDescriptor,
        onConflictStrategy: OnConflictStrategy,
        options: FSOptions
    ): OperationResult<OutputStream> {
        if (!options.isWriteEnabled) {
            return OperationResult.error(
                newGenericIOError(
                    MESSAGE_WRITE_OPERATION_IS_NOT_SUPPORTED,
                    Stacktrace()
                )
            )
        }

        val check = checkPermissionForPath(file.path)
        if (check.isFailed) {
            return check.takeError()
        }

        return lock.withLock {
            try {
                val out = BufferedOutputStream(FileOutputStream(file.path))
                OperationResult.success(out)
            } catch (e: Exception) {
                Timber.d(e)
                OperationResult.error(newGenericIOError(e.message, e))
            }
        }
    }

    private fun checkPermissionForPath(path: String): OperationResult<Unit> {
        if (isPathInsideInternalStorage(path)) {
            return OperationResult.success(Unit)
        }

        if (isPathInsideExternalStorage(path)) {
            return if (Build.VERSION.SDK_INT >= 30) {
                if (Environment.isExternalStorageManager()) {
                    OperationResult.success(Unit)
                } else {
                    OperationResult.error(
                        newPermissionError(
                            MESSAGE_FAILED_TO_ACCESS_TO_FILE,
                            Stacktrace()
                        )
                    )
                }
            } else {
                if (permissionHelper.isPermissionGranted(SystemPermission.SDCARD_PERMISSION)) {
                    OperationResult.success(Unit)
                } else {
                    OperationResult.error(
                        newPermissionError(
                            MESSAGE_FAILED_TO_ACCESS_TO_FILE,
                            Stacktrace()
                        )
                    )
                }
            }
        }

        return if (File(path).exists()) {
            OperationResult.success(Unit)
        } else {
            OperationResult.error(
                newGenericIOError(
                    MESSAGE_FAILED_TO_ACCESS_TO_FILE,
                    Stacktrace()
                )
            )
        }
    }

    private fun getInternalRoot(): String {
        return context.filesDir.path
    }


    private fun isPathInsideInternalStorage(path: String): Boolean {
        val internalRoot = getInternalRoot()
        return path == internalRoot || path.startsWith(internalRoot)
    }

    private fun isPathInsideExternalStorage(path: String): Boolean {
        return getExternalRoots()
            .any { root -> path == root || path.startsWith(root) }
    }

    @Suppress("DEPRECATION")
    private fun getExternalRoots(): List<String> {
        val roots = mutableListOf<String>()

        val external = Environment.getExternalStorageDirectory()
        if (external.exists() && !roots.contains(external.path)) {
            roots.add(external.path)
        }

        val sdcard = File("/sdcard")
        if (sdcard.exists()) {
            roots.add(sdcard.path)
        }

        return roots
    }


}