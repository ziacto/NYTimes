package com.smartdubai.nynews.network.api


import com.smartdubai.nynews.network.response.ApiResponse
import com.smartdubai.nynews.room.ResultResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service API to fetch Article list
 */
interface NewsListService {

    @GET("all-sections/7.json")
    fun fetchArticlesList(@Query("api-key") apiKey: String): Flow<ApiResponse<ResultResponse>>
}