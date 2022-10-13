package org.sjhstudio.naverwebtoon.domain.model

data class WebToon(
    val id: Long,
    val title: String,
    val author: String,
    val favoriteCount: String,
    val thumbnail: String,
    val updated: Boolean
)