package com.rstudios.mutualmobiletask.ui.search

import android.content.Context
import com.rstudios.mutualmobiletask.injection.qualifiers.ActivityContext
import com.rstudios.mutualmobiletask.model.Article
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SearchActivityModule {

  @Binds
  @ActivityContext
  abstract fun provideContext(searchResultsActivity: SearchResultsActivity) : Context

  companion object{
    @Provides
    fun provideNewsRecyclerAdapter(@ActivityContext context: Context) : NewsRecyclerAdapter{
      return NewsRecyclerAdapter(context,arrayListOf<Article>())
    }
  }

}