package org.sjhstudio.naverwebtoon.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.MobileWebtoonService
import org.sjhstudio.naverwebtoon.data.mapperToThumbnail
import org.sjhstudio.naverwebtoon.domain.model.Episode
import javax.inject.Inject

internal class EpisodePagingSource @Inject constructor(
    private val api: MobileWebtoonService,
    private val titleId: Long,
    private val week: String
) : PagingSource<Int, Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val page = params.key ?: 1

        return try {
            val document =
                Jsoup.parse(api.getEpisodeList(titleId, week, page).charStream().readText())
            val episodeElement =
                document.select("ul.section_episode_list.preview").select("li.item")
            val pagingElement = document.select("div.paging_type2")
            val totalPage = pagingElement.select("span.total").text().toIntOrNull() ?: 1
            val episodeList = mutableListOf<Episode>()

            episodeElement.forEach { element ->
                val episode = Episode(
                    titleId = element.attr("data-title-id").toLong(),
                    dataNo = element.attr("data-no").toLong(),
                    thumbnail = mapperToThumbnail(
                        element.select("div.thumbnail > img").attr("src")
                    ),
                    title = element.select("div.info > strong.title > span.name").text(),
                    up = element.select("div.info > strong.title > span.bullet.up")
                        .text()
                        .isNotEmpty(),
                    score = element.select("div.detail > span.score").text(),
                    date = element.select("div.detail > span.date").text(),
                )

                episodeList.add(episode)
            }

            val endOfPaginationReached = totalPage <= page

            if (totalPage >= page) {
                LoadResult.Page(
                    data = episodeList,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (endOfPaginationReached) null else page + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}