package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(){
  suspend fun getEverything(keyword: String) = RetrofitInstance.newsApi.getEverything(keyword)
}