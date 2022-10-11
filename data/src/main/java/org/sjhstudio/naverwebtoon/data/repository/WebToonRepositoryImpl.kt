package org.sjhstudio.naverwebtoon.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.NaverMobileWebToonService
import org.sjhstudio.naverwebtoon.data.api.NaverWebToonService
import org.sjhstudio.naverwebtoon.data.mapperToThumbnail
import org.sjhstudio.naverwebtoon.data.mapperToWebToonId
import org.sjhstudio.naverwebtoon.data.model.Weekday
import org.sjhstudio.naverwebtoon.domain.model.MobileWebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

internal class WebToonRepositoryImpl @Inject constructor(
    private val webToonService: NaverWebToonService,
    private val mobileWebToonService: NaverMobileWebToonService
) : WebToonRepository {

    override fun getWeekdayList(): Flow<Map<String, List<WebToon>>> = flow {
        val map = mutableMapOf<String, List<WebToon>>()

        Weekday.values().forEach { weekday ->
            val list = mutableListOf<WebToon>()

            Jsoup.parse(webToonService.getWeekdayList(weekday.english).charStream().readText())
                .select("ul.img_list")
                .select("li").forEach Jsoup@{ element ->
                    val id = mapperToWebToonId(element.select("div.thumb > a").attr("href"))?.toLongOrNull() ?: return@Jsoup
                    val thumbnail = element.select("div.thumb > a > img").attr("src")
                    val name = element.select("dt > a").text()
                    val writer = element.select("dd.desc > a").text()
                    val rating = element.select("div.rating_type > strong").text()

                    list.add(WebToon(id, name, writer, rating, thumbnail))
                }

            map[weekday.english] = list
        }

        emit(map)
    }

    override fun getMobileWeekdayList(): Flow<Map<String, List<MobileWebToon>>> = flow {
        val map = mutableMapOf<String, List<MobileWebToon>>()

        Weekday.values().forEach { weekday ->
            val list = mutableListOf<MobileWebToon>()

            Jsoup.parse(mobileWebToonService.getWeekdayList(weekday.english).charStream().readText())
                .select("ul.list_toon.type2")
                .select("li.item").forEach Jsoup@{ element ->
                    val id = mapperToWebToonId(element.select("a").attr("href"))?.toLongOrNull() ?: return@Jsoup
                    val thumbnail = mapperToThumbnail(element.select("div.thumbnail > img").attr("src"))
                    val name = element.select("span.title_text").text()
                    val writer = element.select("span.author").text()
                    val rating = element.select("span.favcount").text()
                    val update = element.select("span.blind").isNotEmpty()

                    list.add(MobileWebToon(id, name, writer, rating, thumbnail, update))
                }

            map[weekday.english] = list
        }

        emit(map)
    }
}