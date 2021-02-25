package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(val newsApi: NewsApi){

  suspend fun getHeadLines() = newsApi.getHeadlines()

  suspend fun getSources() = newsApi.getSources()


}