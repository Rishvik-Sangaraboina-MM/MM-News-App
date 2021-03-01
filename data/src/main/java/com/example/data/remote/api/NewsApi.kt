package com.example.data.remote.api

import com.example.data.Constant
import com.example.data.remote.model.NewsResponse
import com.example.data.remote.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
  @GET("v2/top-headlines")
  suspend fun getHeadlines(
      @Query("country")
      countryCode: String = "us",
      @Query("page")
      pages: Int = 1,
      @Query("apiKey")
      key: String = Constant.API_KEY
  ): Response<NewsResponse>

  @GET("v2/everything")
  suspend fun getEverything(
      @Query("q")
      keyword: String,
      @Query("page")
      pages: Int = 1,
      @Query("apiKey")
      key: String = Constant.API_KEY
  ): Response<NewsResponse>

  @GET("v2/sources")
  suspend fun getSources(
      @Query("country")
      countryCode: String = "us",
      @Query("apiKey")
      key: String = Constant.API_KEY
  ): Response<SourceResponse>
}