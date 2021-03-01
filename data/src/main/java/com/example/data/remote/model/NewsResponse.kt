package com.example.data.remote.model

import com.example.data.local.entity.ArticleEntity
import com.google.gson.annotations.SerializedName

data class NewsResponse(
  @SerializedName("articles")
  val articleEntities: List<ArticleEntity>,
  val status: String,
  val totalResults: Int
)