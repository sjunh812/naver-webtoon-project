package org.sjhstudio.naverwebtoon.data.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.naverwebtoon.data.api.NaverWebToonService
import retrofit2.Retrofit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideNaverWebToonApi(retrofit: Retrofit): NaverWebToonService {
        return retrofit.create(NaverWebToonService::class.java)
    }
}