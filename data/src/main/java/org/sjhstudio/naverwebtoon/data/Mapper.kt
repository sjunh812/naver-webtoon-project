package org.sjhstudio.naverwebtoon.data

fun mapperToWebToonId(href: String?): String? {
    if (!href.isNullOrEmpty()) {
        var id = ""
        href.substring(22).forEach { c ->
            if (c == '&' || !Character.isDigit(c)) return id
            else id += c
        }
        return id
    }
    return null
}

fun mapperToThumbnail(originUrl: String) =
    originUrl.replace(
        "image-comic.pstatic.net",
        "shared-comic.pstatic.net/thumb"
    )

fun mapperToNewWebToonColor(rgb: String): List<Int> {
    val list = mutableListOf<Int>()
    try {
        rgb.substring(16, rgb.length - 2).split(",").forEach { codeStr ->
            val code = codeStr.trim()
            list.add(code.toInt())
        }
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return list
}

fun mapperToWebToonInfoColor(style: String): String {
    return style.substring(17, 24)
}