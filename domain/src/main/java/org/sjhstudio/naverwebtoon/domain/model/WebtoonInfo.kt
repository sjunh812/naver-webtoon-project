package org.sjhstudio.naverwebtoon.domain.model

data class WebtoonInfo(
    val id: Long,
    val summary: String,
    val title: String,
    val author: String,
    val score: String,
    val favoriteCount: String,
    val backImageUrl: String,
    val frontImageUrl: String,
    val backgroundImageUrl: String,
    val genre: String,
    val genreDetail: String,
    val weekday: String,
    val age: String,
    val summaryDetail: String,
    val colorCode: String,
    val isAdult: Boolean = false
)