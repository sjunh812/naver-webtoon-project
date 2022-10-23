package org.sjhstudio.naverwebtoon.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NaverMobileWebToonService {

    @GET("weekday")
    suspend fun getWeekdayList(@Query("week") week: String): ResponseBody

    @GET("list")
    suspend fun getEpisodeList(
        @Query("titleId") titleId: Long,
        @Query("week") week: String,
        @Query("page") page: Int = 1,
        @Query("sortOrder") sortOrder: String = "DESC"
    ): ResponseBody
}