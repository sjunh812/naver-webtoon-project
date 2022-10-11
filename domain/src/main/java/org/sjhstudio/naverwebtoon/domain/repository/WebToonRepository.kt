package org.sjhstudio.naverwebtoon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sjhstudio.naverwebtoon.domain.model.MobileWebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToon

interface WebToonRepository {

    fun getWeekdayList(): Flow<Map<String, List<WebToon>>>

    fun getMobileWeekdayList(): Flow<Map<String, List<MobileWebToon>>>
}