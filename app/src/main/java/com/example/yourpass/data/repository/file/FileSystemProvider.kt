package com.example.yourpass.data.repository.file

import com.example.yourpass.data.entity.FileDescriptor
import com.example.yourpass.data.entity.OperationResult
import java.io.InputStream
import java.io.OutputStream

interface FileSystemProvider {

    suspend fun openFileForRead(
        file: FileDescriptor,
        onConflictStrategy: OnConflictStrategy,
        options: FSOptions
    ): OperationResult<InputStream>


    suspend fun openFileForWrite(
        file: FileDescriptor,
        onConflictStrategy: OnConflictStrategy,
        options: FSOptions
    ): OperationResult<OutputStream>

}