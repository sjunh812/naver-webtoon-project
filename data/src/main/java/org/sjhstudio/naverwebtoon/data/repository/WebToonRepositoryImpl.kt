package org.sjhstudio.naverwebtoon.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.NaverMobileWebToonService
import org.sjhstudio.naverwebtoon.data.mapperToNewWebToonColor
import org.sjhstudio.naverwebtoon.data.mapperToThumbnail
import org.sjhstudio.naverwebtoon.data.mapperToWebToonId
import org.sjhstudio.naverwebtoon.data.mapperToWebToonInfoColor
import org.sjhstudio.naverwebtoon.domain.model.NewWebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToonInfo
import org.sjhstudio.naverwebtoon.domain.model.Weekday
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

internal class WebToonRepositoryImpl @Inject constructor(
    private val mobileApi: NaverMobileWebToonService
) : WebToonRepository {

    override fun getWeekdayWebToonList(): Flow<Map<String, List<WebToon>>> = flow {
        val map = mutableMapOf<String, List<WebToon>>()

        Weekday.values().forEach { weekday ->
            val list = mutableListOf<WebToon>()

            Jsoup.parse(mobileApi.getWeekdayList(weekday.english).charStream().readText())
                .select("ul.list_toon.type2")
                .select("li.item").forEach Jsoup@{ element ->
                    val id = mapperToWebToonId(element.select("a").attr("href"))?.toLongOrNull()
                        ?: return@Jsoup
                    val title = element.select("span.title_text").text()
                    val author = element.select("span.author").text()
                    val favCnt = element.select("span.favcount").text()
                    val updated = element.select("span.blind").isNotEmpty()
                    val thumbnail =
                        mapperToThumbnail(element.select("div.thumbnail > img").attr("src"))
                    list.add(
                        WebToon(
                            id = id,
                            title = title,
                            author = author,
                            favoriteCount = favCnt,
                            thumbnail = thumbnail,
                            updated = updated
                        )
                    )
                }
            map[weekday.english] = list
        }
        emit(map)
    }

    override fun getNewWebToonList(html: String): Flow<List<NewWebToon>> = flow {
        val list = mutableListOf<NewWebToon>()

        Jsoup.parse(html)
            .select("div.section_new_webtoon")
            .select("div.eg-flick-panel").forEach { element ->
                val id = mapperToWebToonId(element.select("a").attr("href"))?.toLongOrNull()
                    ?: return@forEach
                val title = element.select("strong.title").text()
                val author = element.select("span.author").text()
                val summary = element.select("p.summary").text()
                val colorList = mapperToNewWebToonColor(element.select("a").attr("style"))
                val backgroundThumbnail =
                    mapperToThumbnail(element.select("div.thumb_bg > img").attr("src"))
                var frontThumbnail = ""
                var backThumbnail = ""
                element.select("div.thumbnail > img").forEach { thumbnailElement ->
                    val originUrl = thumbnailElement.attr("src")
                    if (originUrl.contains("backImage")) backThumbnail =
                        mapperToThumbnail(originUrl)
                    else if (originUrl.contains("frontImage")) frontThumbnail =
                        mapperToThumbnail(originUrl)
                }
                list.add(
                    NewWebToon(
                        id = id,
                        title = title,
                        author = author,
                        summary = summary,
                        frontThumbnail = frontThumbnail,
                        backThumbnail = backThumbnail,
                        backgroundThumbnail = backgroundThumbnail,
                        colorList = colorList
                    )
                )
            }
        emit(list)
    }

    override fun getWebToonInfo(titleId: Long, week: String): Flow<WebToonInfo> = flow {
        println("xxx ?")
        val rootElement =
            Jsoup.parse(mobileApi.getEpisodeList(titleId, week).charStream().readText())
                .select("div.section_toon_info")
        val infoElement = rootElement.select("div.area_info")
        val infoBackElement = rootElement.select("div.info_back")
        val thumbnailElement = rootElement.select("div.area_thumbnail")
        var frontThumbnail = ""
        var backThumbnail = ""

        thumbnailElement.select("img").forEach { element ->
            val url = element.attr("src")
            if (url.contains("backImage")) backThumbnail = mapperToThumbnail(url)
            else if (url.contains("frontImage")) frontThumbnail = mapperToThumbnail(url)
        }

        val webToonInfo = WebToonInfo(
            id = titleId,
            summary = infoElement.select("span.summary").text(),
            title = infoElement.select("strong.title").text(),
            author = infoElement.select("span.author").text(),
            score = infoElement.select("span.score").text(),
            favCount = infoElement.select("span.favcount").text(),
            backImageUrl = backThumbnail,
            frontImageUrl = frontThumbnail,
            backgroundImageUrl = thumbnailElement.select("div.thumb_bg img").attr("src"),
            genre = infoBackElement.select("div.genre span.length").text(),
            genreDetail = infoBackElement.select("div.genre ul.property.list_detail li").text(),
            weekday = infoBackElement.select("div.week_day ul.list_detail li").text(),
            age = infoBackElement.select("ul.property.list_detail.age li").text(),
            summaryDetail = infoBackElement.select("div.summary > p").text(),
            color = mapperToWebToonInfoColor(rootElement.attr("style"))
        )

        println("xxx webToonInfo: $webToonInfo")
        emit(webToonInfo)
    }
}