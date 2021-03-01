package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
  val author: String?,
  val content: String?,
  val description: String?,
  val publishedAt: String?,
  @Embedded(prefix = "source_")
  val sourceEntity: SourceEntity?,
  @PrimaryKey
  val title: String,
  val url: String?,
  val urlToImage: String?
)