package org.sjhstudio.naverwebtoon.data.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.naverwebtoon.data.api.NaverMobileWebToonService
import org.sjhstudio.naverwebtoon.data.source.EpisodePagingSource
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object PagingSourceModule {

    @Singleton
    @Provides
    fun provideEpisodePagingSource(
        api: NaverMobileWebToonService,
        titleId: Long,
        week: String
    ): EpisodePagingSource = EpisodePagingSource(api, titleId, week)
}