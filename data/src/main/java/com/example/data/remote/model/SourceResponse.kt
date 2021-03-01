package com.example.data.remote.model

import com.example.data.local.entity.SourceXEntity
import com.google.gson.annotations.SerializedName

data class SourceResponse(
  @SerializedName("sources")
  val sourceEntities: List<SourceXEntity>,
  val status: String
)