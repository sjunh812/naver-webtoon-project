package org.sjhstudio.naverwebtoon.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.NaverWebToonApi
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

internal class WebToonRepositoryImpl @Inject constructor(
    private val api: NaverWebToonApi
) : WebToonRepository {

    override fun getWeekdayList(week: String): Flow<List<WebToon>> = flow {
        Jsoup.parse(api.getWeekdayList(week).charStream().readText())
            .select("ul.img_list")
            .select("li").forEach { element ->
                val thumbHref = element.select("div.thumb > a").attr("href")
                    .substring(22)
                var idStr = ""
                for (i in thumbHref.indices) {
                    val c = thumbHref[i]
                    if (c == '&' || !Character.isDigit(c)) break
                    else idStr += c
                }
                val id = idStr.toLongOrNull() ?: 0
                val name = element.select("dt > a").text()
                val writer = element.select("dd.desc > a").text()
                val rating = element.select("div.rating_type > strong").text()
                val thumbnail = element.select("div.thumb > a > img").attr("src")

                println("xxx id: $id, name: $name, writer: $writer, rating: $rating, thumbnail: $thumbnail")
            }
    }
}