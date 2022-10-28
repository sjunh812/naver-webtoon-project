package org.sjhstudio.naverwebtoon.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.NaverMobileWebToonService
import org.sjhstudio.naverwebtoon.data.mapperToNewWebToonColor
import org.sjhstudio.naverwebtoon.data.mapperToThumbnail
import org.sjhstudio.naverwebtoon.data.mapperToWebToonId
import org.sjhstudio.naverwebtoon.data.mapperToWebToonInfoColor
import org.sjhstudio.naverwebtoon.data.source.EpisodePagingSource
import org.sjhstudio.naverwebtoon.domain.model.*
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

internal class WebToonRepositoryImpl @Inject constructor(
    private val mobileApi: NaverMobileWebToonService
) : WebToonRepository {

    override fun getWeekdayWebToonList(): Flow<Map<String, List<WeekdayWebtoon>>> = flow {
        val map = mutableMapOf<String, List<WeekdayWebtoon>>()

        Weekday.values().forEach { weekday ->
            val list = mutableListOf<WeekdayWebtoon>()

            Jsoup.parse(mobileApi.getWeekdayList(weekday.english).charStream().readText())
                .select("ul.list_toon.type2")
                .select("li.item").forEach Jsoup@{ element ->
                    val id = mapperToWebToonId(element.select("a").attr("href"))?.toLongOrNull()
                        ?: return@Jsoup
                    list.add(
                        WeekdayWebtoon(
                            id = id,
                            title = element.select("span.title_text").text(),
                            author = element.select("span.author").text(),
                            favoriteCount = element.select("span.favcount").text(),
                            thumbnail = mapperToThumbnail(
                                element.select("div.thumbnail > img").attr("src")
                            ),
                            isUpdated = element.select("span.blind").isNotEmpty()
                        )
                    )
                }
            map[weekday.english] = list
        }
        emit(map)
    }

    override fun getNewWebToonList(html: String): Flow<List<NewWebtoon>> = flow {
        val list = mutableListOf<NewWebtoon>()

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
                    NewWebtoon(
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

    override fun getWebToonInfo(titleId: Long, week: String): Flow<WebtoonInfo> = flow {
        val document = Jsoup.parse(mobileApi.getEpisodeList(titleId, week).charStream().readText())
        val rootElement = document.select("div.section_toon_info")
        val infoElement = rootElement.select("div.area_info")
        val infoBackElement = rootElement.select("div.info_back")
        val thumbnailElement = rootElement.select("div.area_thumbnail")
        var frontThumbnail = ""
        var backThumbnail = ""

        thumbnailElement.select("img").forEach { element ->
            val url = element.attr("src")
            if (url.contains("back")) backThumbnail = mapperToThumbnail(url)
            else if (url.contains("front")) frontThumbnail = mapperToThumbnail(url)
        }

        val webToonInfo = WebtoonInfo(
            id = titleId,
            summary = infoElement.select("span.summary").text(),
            title = infoElement.select("strong.title").text(),
            author = infoElement.select("span.author").text(),
            score = infoElement.select("span.score").text(),
            favoriteCount = infoElement.select("span.favcount").text(),
            backImageUrl = backThumbnail,
            frontImageUrl = frontThumbnail,
            backgroundImageUrl = mapperToThumbnail(
                thumbnailElement.select("div.thumb_bg img").attr("src")
            ),
            genre = infoBackElement.select("div.genre span.length").text(),
            genreDetail = infoBackElement.select("div.genre ul.property.list_detail li").text(),
            weekday = infoBackElement.select("div.week_day ul.list_detail li").first()?.text()
                ?: "",
            age = infoBackElement.select("ul.property.list_detail.age li").text(),
            summaryDetail = infoBackElement.select("div.summary > p").text(),
            colorCode = mapperToWebToonInfoColor(rootElement.attr("style")),
            isAdult = document.select("p.login_desc").attr("id").isNotEmpty()
        )

        println("xxx webToonInfo: $webToonInfo")
        emit(webToonInfo)
    }

    override fun getEpisodePagingData(
        titleId: Long,
        week: String
    ): Flow<PagingData<Episode>> {
        return Pager(config = PagingConfig(pageSize = EPISODE_PAGE_SIZE)) {
            EpisodePagingSource(mobileApi, titleId, week)
        }.flow
    }

    companion object {

        private const val EPISODE_PAGE_SIZE = 25
    }
}