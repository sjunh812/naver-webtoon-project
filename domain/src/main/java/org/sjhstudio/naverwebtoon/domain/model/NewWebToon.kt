package org.sjhstudio.naverwebtoon.domain.model

data class NewWebToon(
    val id: Long,
    val title: String,
    val author: String,
    val summary: String,
    val frontThumbnail: String,
    val backThumbnail: String,
    val backgroundThumbnail: String,
    val colorList: List<Int>
)