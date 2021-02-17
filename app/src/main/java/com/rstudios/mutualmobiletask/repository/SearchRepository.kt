package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.RetrofitInstance

class SearchRepository {
    suspend fun getEverything(keyword : String) = RetrofitInstance.newsApi.getEverything(keyword)
}