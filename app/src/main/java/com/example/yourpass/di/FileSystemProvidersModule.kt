package com.example.yourpass.di

import android.content.Context
import com.example.yourpass.data.repository.FileSystemResolver
import com.ivanovsky.passnotes.data.entity.FSType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object FileSystemProvidersModule {
//
//    @Provides
//    @Singleton
//    fun provideFileSystemResolver(
//        @ApplicationContext context: Context
//    ): FileSystemResolver {
//        val factories: Map<FSType, FileSystemResolver.Factory> =
//            FileSystemResolver.buildFactories(
//                isExternalStorageAccessEnabled = true,
//                context = context
//            )
//        return FileSystemResolver(factories, context)
//    }
//}