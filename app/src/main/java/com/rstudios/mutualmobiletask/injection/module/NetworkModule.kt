package com.rstudios.mutualmobiletask.injection.module

import com.rstudios.mutualmobiletask.api.NewsApi
import com.rstudios.mutualmobiletask.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
  @Singleton
  @Provides
  fun provideConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
  }

  @Singleton
  @Provides
  fun provideRetrofitInstance(gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }
}