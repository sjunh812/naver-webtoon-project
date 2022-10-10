package org.sjhstudio.naverwebtoon.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.NaverWebToonService
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

internal class WebToonRepositoryImpl @Inject constructor(
    private val webToonService: NaverWebToonService
) : WebToonRepository {

    override fun getWeekdayList(week: String): Flow<List<WebToon>> = flow {
        val list = mutableListOf<WebToon>()
        Jsoup.parse(webToonService.getWeekdayList(week).charStream().readText())
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

                list.add(WebToon(id, name, writer, rating, thumbnail))
            }
        emit(list)
    }
}