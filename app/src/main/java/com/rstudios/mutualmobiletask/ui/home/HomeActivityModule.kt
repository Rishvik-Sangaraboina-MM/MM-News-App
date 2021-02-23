package com.rstudios.mutualmobiletask.ui.home

import android.content.Context
import com.rstudios.mutualmobiletask.injection.qualifiers.ActivityContext
import com.rstudios.mutualmobiletask.model.Article
import com.rstudios.mutualmobiletask.model.SourceX
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.utils.SourceRecyclerAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {
  @ContributesAndroidInjector
  abstract fun bindHeadlinesFragment() : HeadlinesFragment
  @ContributesAndroidInjector
  abstract fun bindSourceFragment() : SourceFragment

  @Binds
  @ActivityContext
  abstract fun provideContext(homeActivity: HomeActivity) : Context

  companion object{
    @Provides
    fun provideNewsRecyclerAdapter(@ActivityContext context: Context) : NewsRecyclerAdapter{
      return NewsRecyclerAdapter(context,arrayListOf<Article>())
    }

    @Provides
    fun provideSourceRecyclerAdapter(@ActivityContext context: Context) : SourceRecyclerAdapter{
      return SourceRecyclerAdapter(context, arrayListOf<SourceX>())
    }
  }


}