package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.SourceXEntity

@Dao
interface SourceXDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSourceX(sourceXEntity : SourceXEntity)

  @Query("SELECT * FROM SourceXEntity LIMIT 20")
  suspend fun getSourceX() : List<SourceXEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMulSourceX(list: List<SourceXEntity>)
}