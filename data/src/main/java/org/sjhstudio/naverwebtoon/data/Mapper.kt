package org.sjhstudio.naverwebtoon.data

fun mapperToWebToonId(href: String?): String? {
    if (!href.isNullOrEmpty()) {
        var id = ""
        href.substring(22).forEach { c ->
            if (c == '&' || !Character.isDigit(c)) return id
            else id += c
        }
    }
    return null
}

fun mapperToThumbnail(originUrl: String) =
    originUrl.replace(
        "image-comic.pstatic.net",
        "shared-comic.pstatic.net/thumb"
    )