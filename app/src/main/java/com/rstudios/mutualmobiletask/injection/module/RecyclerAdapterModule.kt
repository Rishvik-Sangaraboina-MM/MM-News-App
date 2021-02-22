package com.rstudios.mutualmobiletask.injection.module

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rstudios.mutualmobiletask.model.Article
import com.rstudios.mutualmobiletask.utils.NewsRecyclerAdapter
import com.rstudios.mutualmobiletask.utils.SourceRecyclerAdapter
import dagger.Module
import dagger.Provides

@Module
class RecyclerAdapterModule {

  @Provides
  fun provideRecyclerAdapter(context: Context) : NewsRecyclerAdapter {
    return NewsRecyclerAdapter(context, arrayListOf<Article>())
  }

  @Provides
  fun provideSrcAdapter() : SourceRecyclerAdapter {
    return SourceRecyclerAdapter()
  }

}