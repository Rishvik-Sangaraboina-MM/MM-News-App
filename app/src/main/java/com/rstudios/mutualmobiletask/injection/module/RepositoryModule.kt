package com.rstudios.mutualmobiletask.injection.module

import com.example.data.local.NewsDB
import com.example.data.remote.api.NewsApi
import com.example.data.HomeRepository
import com.example.data.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

  @Provides
  fun provideHomeRepository(newsApi: NewsApi,newsDB: NewsDB) = HomeRepository(newsApi,newsDB)

  @Provides
  fun provideSearchRepository(newsApi: NewsApi,newsDB: NewsDB) = SearchRepository(newsApi,newsDB)
}