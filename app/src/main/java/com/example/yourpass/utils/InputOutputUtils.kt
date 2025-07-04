package com.example.yourpass.utils

import com.example.yourpass.data.entity.OperationError.newGenericIOError
import com.example.yourpass.data.entity.OperationResult
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.Throws
import timber.log.Timber

object InputOutputUtils {

    private const val BUFFER_SIZE = 1024 * 8
    private val UNCANCELABLE = AtomicBoolean(false)

    @JvmStatic
    fun newFileInputStreamOrNull(file: File): FileInputStream? {
        return try {
            FileInputStream(file)
        } catch (e: FileNotFoundException) {
            Timber.d(e)
            null
        }
    }

    @JvmStatic
    fun newFileOutputStreamOrNull(file: File): FileOutputStream? {
        return try {
            FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            Timber.d(e)
            null
        }
    }

    fun copy(
        from: InputStream,
        to: OutputStream,
        isClose: Boolean
    ): OperationResult<Unit> {
        return try {
            copyOrThrow(
                from = from,
                to = to,
                isCloseOnFinish = isClose,
                cancellation = UNCANCELABLE
            )
            OperationResult.success(Unit)
        } catch (exception: IOException) {
            Timber.d(exception)
            OperationResult.error(newGenericIOError(exception))
        }
    }

    @JvmStatic
    fun copy(
        sourceFile: File,
        destinationFile: File
    ): OperationResult<Unit> {
        val source = try {
            FileInputStream(sourceFile)
        } catch (exception: FileNotFoundException) {
            Timber.d(exception)
            return OperationResult.error(newGenericIOError(exception))
        }

        val destination = try {
            FileOutputStream(destinationFile, false)
        } catch (exception: FileNotFoundException) {
            Timber.d(exception)
            return OperationResult.error(newGenericIOError(exception))
        }

        return try {
            copyOrThrow(
                source,
                destination,
                isCloseOnFinish = true,
                cancellation = UNCANCELABLE
            )
            OperationResult.success(Unit)
        } catch (exception: IOException) {
            Timber.d(exception)
            OperationResult.error(newGenericIOError(exception))
        }
    }

    @JvmStatic
    fun copy(
        source: InputStream,
        destinationFile: File
    ): OperationResult<Unit> {
        val destination = try {
            FileOutputStream(destinationFile, false)
        } catch (exception: FileNotFoundException) {
            Timber.d(exception)
            return OperationResult.error(newGenericIOError(exception))
        }

        return try {
            copyOrThrow(
                source,
                destination,
                isCloseOnFinish = true,
                cancellation = UNCANCELABLE
            )
            OperationResult.success(Unit)
        } catch (exception: IOException) {
            Timber.d(exception)
            OperationResult.error(newGenericIOError(exception))
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyOrThrow(
        source: InputStream,
        destination: OutputStream,
        isCloneOnFinish: Boolean
    ) {
        copyOrThrow(source, destination, isCloneOnFinish, cancellation = UNCANCELABLE)
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyOrThrow(
        from: InputStream,
        to: OutputStream,
        isCloseOnFinish: Boolean,
        cancellation: AtomicBoolean
    ) {
        try {
            val buf = ByteArray(BUFFER_SIZE)
            var len: Int
            while (from.read(buf).also { len = it } > 0 && !cancellation.get()) {
                to.write(buf, 0, len)
            }
            to.flush()
        } finally {
            if (isCloseOnFinish) {
                closeOrThrow(from)
                closeOrThrow(to)
            }
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun closeOrThrow(closeable: Closeable?) {
        if (closeable == null) {
            return
        }

        closeable.close()
    }

    @JvmStatic
    fun close(closeable: Closeable?) {
        if (closeable == null) {
            return
        }

        try {
            closeable.close()
        } catch (e: IOException) {
            Timber.d(e)
        }
    }

    @JvmStatic
    fun readAllBytes(
        source: InputStream,
        isCloseOnFinish: Boolean
    ): OperationResult<ByteArray> {
        return try {
            val bytes = source.readBytes()

            if (isCloseOnFinish) {
                close(source)
            }

            OperationResult.success(bytes)
        } catch (e: IOException) {
            Timber.e(e)
            OperationResult.error(newGenericIOError(e))
        }
    }
}