package org.sjhstudio.naverwebtoon.domain.model

data class MobileWebToon(
    val id: Long,
    val name: String,
    val writer: String,
    val favoriteCount: String,
    val thumbnail: String,
    val update: Boolean
)