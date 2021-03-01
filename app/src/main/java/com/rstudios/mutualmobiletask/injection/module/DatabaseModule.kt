package com.rstudios.mutualmobiletask.injection.module

import android.content.Context
import androidx.room.Room
import com.example.data.local.NewsDB
import com.rstudios.mutualmobiletask.injection.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

  @Singleton
  @Provides
  internal fun provideDB(@ApplicationContext context: Context) : NewsDB{
    return Room.databaseBuilder(context,NewsDB::class.java,NewsDB.DB_NAME).build()
  }
}