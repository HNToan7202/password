package com.example.yourpass.data.repository.file;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yourpass.data.entity.ConflictResolutionStrategy;
import com.example.yourpass.data.entity.FileDescriptor;
import com.example.yourpass.data.entity.OperationResult;
import com.example.yourpass.data.entity.SyncConflictInfo;
import com.example.yourpass.data.entity.SyncProgressStatus;
import com.example.yourpass.data.entity.SyncStatus;

public interface FileSystemSyncProcessor {

    @Nullable
    FileDescriptor getCachedFile(@NonNull String uid);

    @NonNull
    SyncProgressStatus getSyncProgressStatusForFile(@NonNull String uid);

    @NonNull
    SyncStatus getSyncStatusForFile(@NonNull String uid);

    @Nullable
    String getRevision(@NonNull String uid);

    @NonNull
    OperationResult<SyncConflictInfo> getSyncConflictForFile(@NonNull String uid);

    /**
     * Returns updated FileDescriptor
     */
    @NonNull
    OperationResult<FileDescriptor> process(
            @NonNull FileDescriptor file,
            @NonNull SyncStrategy syncStrategy,
            @Nullable ConflictResolutionStrategy resolutionStrategy);
}
