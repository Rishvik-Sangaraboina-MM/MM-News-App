package com.example.data

import com.example.data.local.NewsDB
import com.example.data.remote.api.NewsApi

class SearchRepository constructor(val newsApi: NewsApi, newsDB: NewsDB) {

  suspend fun getRemoteEverything(keyword: String) = newsApi.getEverything(keyword)

}