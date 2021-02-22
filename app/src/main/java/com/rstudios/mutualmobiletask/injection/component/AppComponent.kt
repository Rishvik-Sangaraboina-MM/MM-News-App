package com.rstudios.mutualmobiletask.injection.component

import android.content.Context
import com.rstudios.mutualmobiletask.injection.module.RecyclerAdapterModule
import com.rstudios.mutualmobiletask.ui.home.HeadlinesFragment
import com.rstudios.mutualmobiletask.utils.MyApplication
import com.rstudios.mutualmobiletask.ui.home.HomeActivity
import com.rstudios.mutualmobiletask.ui.home.SourceFragment
import com.rstudios.mutualmobiletask.ui.search.SearchResultsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RecyclerAdapterModule::class])
interface AppComponent {

  @Component.Factory
  interface Factory{
    fun create(@BindsInstance application: MyApplication, @BindsInstance context: Context) : AppComponent
  }

  fun inject(activity: HomeActivity)

  fun inject(activity: SearchResultsActivity)

  fun inject(headlinesFragment: HeadlinesFragment)

  fun inject(sourceFragment: SourceFragment)

}