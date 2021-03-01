package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

  @Query("SELECT * FROM ArticleEntity LIMIT 20")
  suspend fun getArticles() : List<ArticleEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertArticle(articleEntity: ArticleEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMulArticle(list : List<ArticleEntity>)


}