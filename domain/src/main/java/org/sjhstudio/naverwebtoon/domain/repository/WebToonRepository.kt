package org.sjhstudio.naverwebtoon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.naverwebtoon.domain.model.NewWebtoon
import org.sjhstudio.naverwebtoon.domain.model.WeekdayWebtoon
import org.sjhstudio.naverwebtoon.domain.model.WebToonInfo

interface WebToonRepository {

    fun getWeekdayWebToonList(): Flow<Map<String, List<WeekdayWebtoon>>>

    fun getNewWebToonList(html: String): Flow<List<NewWebtoon>>

    fun getWebToonInfo(titleId: Long, week: String): Flow<WebToonInfo>
}