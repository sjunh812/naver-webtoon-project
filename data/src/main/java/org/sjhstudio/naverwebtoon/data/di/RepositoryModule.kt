package org.sjhstudio.naverwebtoon.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.naverwebtoon.data.repository.WebtoonRepositoryImpl
import org.sjhstudio.naverwebtoon.domain.repository.WebtoonRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWebtoonRepository(webToonRepositoryImpl: WebtoonRepositoryImpl): WebtoonRepository
}