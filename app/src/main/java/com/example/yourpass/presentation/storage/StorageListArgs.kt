package com.example.yourpass.presentation.storage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StorageListArgs(
    val action: Action,
    val resultKey: String
) : Parcelable