package com.example.yourpass.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class) // Cung cấp context cho toàn bộ ứng dụng
object AppModule {

    @Provides
    fun provideContext(application: Context): Context {
        return application
    }
}