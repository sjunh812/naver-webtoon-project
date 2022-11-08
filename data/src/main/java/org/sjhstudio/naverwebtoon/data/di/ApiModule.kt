package org.sjhstudio.naverwebtoon.data.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.naverwebtoon.data.api.MobileWebtoonService
import retrofit2.Retrofit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideMobileWebtoonService(@MobileUrl retrofit: Retrofit): MobileWebtoonService =
        retrofit.create(MobileWebtoonService::class.java)
}