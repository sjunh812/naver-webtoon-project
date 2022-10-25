package org.sjhstudio.naverwebtoon.domain.model

data class WebToonInfo(
    val id: Long,
    val summary: String,
    val title: String,
    val author: String,
    val score: String,
    val favCount: String,
    val backImageUrl: String,
    val frontImageUrl: String,
    val backgroundImageUrl: String,
    val genre: String,
    val genreDetail: String,
    val weekday: String,
    val age: String,
    val summaryDetail: String,
    val color: String,
    val adult: Boolean = false
)