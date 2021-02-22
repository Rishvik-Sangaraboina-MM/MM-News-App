package com.rstudios.mutualmobiletask.api

import com.rstudios.mutualmobiletask.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val newsApi: NewsApi by lazy { retrofit.create(NewsApi::class.java) }
}