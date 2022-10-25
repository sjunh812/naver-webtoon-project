package org.sjhstudio.naverwebtoon.domain.model

data class WeekdayWebtoon(
    val id: Long,
    val title: String,
    val author: String,
    val favoriteCount: String,
    val thumbnail: String,
    val isUpdated: Boolean
)