package org.sjhstudio.naverwebtoon.domain.model

data class WebToon(
    val id: Long,
    val name: String,
    val writer: String,
    val rating: String,
    val thumbnail: String
)