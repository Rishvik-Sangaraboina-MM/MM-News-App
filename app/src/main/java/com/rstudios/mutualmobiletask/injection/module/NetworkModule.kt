package com.rstudios.mutualmobiletask.injection.module

import com.example.data.remote.api.NewsApi
import com.rstudios.mutualmobiletask.BuildConfig
import com.rstudios.mutualmobiletask.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
  fun provideRetrofitInstance(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Provides
  fun provideHttpClient():OkHttpClient{
    val okHttpLoggingInterceptor = HttpLoggingInterceptor()
      .apply {
        level =
          if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
          else
            HttpLoggingInterceptor.Level.NONE
      }
    return OkHttpClient.Builder()
      .addInterceptor(okHttpLoggingInterceptor)
      .build()
  }

  @Singleton
  @Provides
  fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }
}