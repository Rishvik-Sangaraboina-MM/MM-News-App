package com.rstudios.mutualmobiletask.injection.module

import com.rstudios.mutualmobiletask.injection.scope.ActivityScope
import com.rstudios.mutualmobiletask.ui.home.HomeActivity
import com.rstudios.mutualmobiletask.ui.home.HomeActivityModule
import com.rstudios.mutualmobiletask.ui.search.SearchActivityModule
import com.rstudios.mutualmobiletask.ui.search.SearchResultsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

  @ContributesAndroidInjector(modules = [HomeActivityModule::class])
  abstract fun bindHomeActivity() : HomeActivity

  @ContributesAndroidInjector(modules = [SearchActivityModule::class])
  abstract fun bindSearchResultsActivity() : SearchResultsActivity

}