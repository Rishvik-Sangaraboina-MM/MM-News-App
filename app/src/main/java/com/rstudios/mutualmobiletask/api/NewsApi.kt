package com.rstudios.mutualmobiletask.api

import com.rstudios.mutualmobiletask.model.NewsResponse
import com.rstudios.mutualmobiletask.model.SourceResponse
import com.rstudios.mutualmobiletask.utils.Constants
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
      key: String = Constants.API_KEY
  ): Response<NewsResponse>

  @GET("v2/everything")
  suspend fun getEverything(
      @Query("q")
      keyword: String,
      @Query("page")
      pages: Int = 1,
      @Query("apiKey")
      key: String = Constants.API_KEY
  ): Response<NewsResponse>

  @GET("v2/sources")
  suspend fun getSources(
      @Query("country")
      countryCode: String = "us",
      @Query("apiKey")
      key: String = Constants.API_KEY
  ): Response<SourceResponse>
}