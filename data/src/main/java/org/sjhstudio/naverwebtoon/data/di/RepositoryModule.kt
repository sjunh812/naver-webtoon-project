package org.sjhstudio.naverwebtoon.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import org.sjhstudio.naverwebtoon.repository.WebToonRepositoryImpl
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWebToonRepository(webToonRepositoryImpl: WebToonRepositoryImpl): WebToonRepository
}