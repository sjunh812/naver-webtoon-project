package org.sjhstudio.naverwebtoon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.naverwebtoon.domain.model.WebToon

interface WebToonRepository {

    fun getWeekdayList(week: String): Flow<List<WebToon>>
}