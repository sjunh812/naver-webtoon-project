package org.sjhstudio.naverwebtoon.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.jsoup.Jsoup
import org.sjhstudio.naverwebtoon.data.api.MobileWebtoonService
import org.sjhstudio.naverwebtoon.data.mapperToThumbnail
import javax.inject.Inject

internal class ViewerPagingSource @Inject constructor(
    private val api: MobileWebtoonService,
    private val titleId: Long,
    private val dataNo: Long
) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val page = params.key ?: 0

        return try {
            val document = Jsoup.parse(api.getViewer(titleId, dataNo).charStream().readText())
            val list = document.select("div.toon_view_lst li img").map { element ->
                mapperToThumbnail(element.attr("data-src"))
            }
//            val start = page * 10
//            val end = if (start > list.size) list.size else start + 10
//            println("xxx list(start: $start, end: $end): ${list.subList(page, end)}")
            if (page <= list.size - 1) {
                LoadResult.Page(
                    data = list.subList(page, page + 1),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (page == list.size - 1) null else page + 1
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

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}