package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.ArticleDao
import com.example.data.local.dao.SourceXDao
import com.example.data.local.entity.ArticleEntity
import com.example.data.local.entity.SourceXEntity

@Database(entities = [ArticleEntity::class,SourceXEntity::class], version = 2)
abstract class NewsDB : RoomDatabase() {
  companion object{
    const val DB_NAME="news_db"
  }

  abstract fun getArticleDao() : ArticleDao

  abstract fun getSourceXDao() : SourceXDao

}