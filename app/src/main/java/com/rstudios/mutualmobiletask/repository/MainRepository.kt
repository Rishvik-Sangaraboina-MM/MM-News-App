package com.rstudios.mutualmobiletask.repository

import com.rstudios.mutualmobiletask.api.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(){

  suspend fun getHeadLines() = RetrofitInstance.newsApi.getHeadlines()

  suspend fun getSources() = RetrofitInstance.newsApi.getSources()



}