package org.sjhstudio.naverwebtoon.domain.model

data class Episode(
    val titleId: Long,
    val dataNo: Long,
    val thumbnail: String,
    val title: String,
    val score: String,
    val date: String,
    val up: Boolean
)