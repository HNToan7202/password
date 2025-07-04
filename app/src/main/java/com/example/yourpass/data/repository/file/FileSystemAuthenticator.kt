package com.example.yourpass.data.repository.file

import android.content.Context
import com.example.yourpass.data.entity.FSAuthority
import com.example.yourpass.data.entity.FSCredentials

interface FileSystemAuthenticator {
    fun getAuthType(): AuthType
    fun getFsAuthority(): FSAuthority
    fun isAuthenticationRequired(): Boolean
    fun startAuthActivity(context: Context)
    fun setCredentials(credentials: FSCredentials?)
}