package com.example.yourpass.data.entity

data class SyncConflictInfo(
    val localFile: FileDescriptor,
    val remoteFile: FileDescriptor
)