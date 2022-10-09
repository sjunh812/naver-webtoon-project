package org.sjhstudio.naverwebtoon.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NaverWebToonApi {

    @GET("weekdayList")
    suspend fun getWeekdayList(@Query("week") week: String): ResponseBody
}