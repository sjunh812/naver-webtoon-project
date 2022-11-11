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
        val page = params.key ?: 1

        return try {
            val document = Jsoup.parse(api.getViewer(titleId, dataNo).charStream().readText())
            val list = document.select("div.toon_view_lst li img").map { element ->
                mapperToThumbnail(element.attr("data-src"))
            }.takeIf { list ->
                list.isNotEmpty()
            } ?: document.select("div.viewer.cuttoon div.cut_flick_wrap img").map { element ->
                mapperToThumbnail(element.attr("data-src"))
            }

            println("xxx list: $list")

            val totalPage = list.size / RECORD + if (list.size % RECORD == 0) 0 else 1
            val start = (page - 1) * RECORD
            val end = if (start + RECORD >= list.size) list.size else start + RECORD

            if (page <= totalPage) {
                val subList = list.subList(start, end)

                LoadResult.Page(
                    data = subList,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == totalPage) null else page + 1
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

    companion object {
        private const val RECORD = 15
    }
}