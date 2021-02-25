package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(val newsApi: NewsApi) {
  suspend fun getEverything(keyword: String) = newsApi.getEverything(keyword)
}