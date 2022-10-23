package org.sjhstudio.naverwebtoon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.naverwebtoon.domain.model.NewWebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToonInfo

interface WebToonRepository {

    fun getWeekdayWebToonList(): Flow<Map<String, List<WebToon>>>

    fun getNewWebToonList(html: String): Flow<List<NewWebToon>>

    fun getWebToonInfo(titleId: Long, week: String): Flow<WebToonInfo>
}