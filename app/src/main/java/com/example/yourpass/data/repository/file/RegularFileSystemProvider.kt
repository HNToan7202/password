package com.example.yourpass.data.repository.file


import android.content.Context
import com.example.yourpass.data.entity.FSAuthority


class RegularFileSystemProvider(
    private val context: Context,
    private val fsAuthority: FSAuthority
) : FileSystemProvider {

}