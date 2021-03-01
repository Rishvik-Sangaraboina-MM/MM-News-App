package com.example.data

import com.example.data.local.NewsDB
import com.example.data.local.entity.ArticleEntity
import com.example.data.local.entity.SourceXEntity
import com.example.data.remote.api.NewsApi

class HomeRepository constructor(
  val newsApi: NewsApi,
  val newsDB: NewsDB
) {

  suspend fun getRemoteHeadLines() = newsApi.getHeadlines()

  suspend fun getRemoteSources() = newsApi.getSources()

  suspend fun getLocalHeadLines() = newsDB.getArticleDao().getArticles()

  suspend fun getLocalSources() = newsDB.getSourceXDao().getSourceX()

  suspend fun putLocalHeadLines(list: List<ArticleEntity>?) {
    list?.let {
      newsDB.getArticleDao().insertMulArticle(it)
    }
  }

  suspend fun putLocalSources(list: List<SourceXEntity>?) {
    list?.let {
      newsDB.getSourceXDao().insertMulSourceX(it)
    }
  }
}