package org.sjhstudio.naverwebtoon.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.sjhstudio.naverwebtoon.domain.model.Episode
import org.sjhstudio.naverwebtoon.domain.model.NewWebtoon
import org.sjhstudio.naverwebtoon.domain.model.WebtoonInfo
import org.sjhstudio.naverwebtoon.domain.model.WeekdayWebtoon

interface WebtoonRepository {

    fun getWeekdayWebToonList(): Flow<Map<String, List<WeekdayWebtoon>>>

    fun getNewWebToonList(html: String): Flow<List<NewWebtoon>>

    fun getWebToonInfo(titleId: Long, week: String): Flow<WebtoonInfo>

    fun getEpisodePagingData(titleId: Long, week: String): Flow<PagingData<Episode>>
}