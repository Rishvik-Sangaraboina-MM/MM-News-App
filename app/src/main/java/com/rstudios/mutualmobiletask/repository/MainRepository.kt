package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.RetrofitInstance

class MainRepository {
    suspend fun getHeadLines() = RetrofitInstance.newsApi.getHeadlines()

    suspend fun getSources() = RetrofitInstance.newsApi.getSources()

}