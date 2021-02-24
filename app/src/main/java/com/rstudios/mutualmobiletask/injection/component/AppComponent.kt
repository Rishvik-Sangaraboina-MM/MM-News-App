package com.rstudios.mutualmobiletask.injection.component

import android.content.Context
import com.rstudios.mutualmobiletask.BaseApplication
import com.rstudios.mutualmobiletask.injection.module.ActivityBindingModule
import com.rstudios.mutualmobiletask.injection.module.AppModule
import com.rstudios.mutualmobiletask.injection.qualifiers.ApplicationContext
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBindingModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

  @Component.Factory
  interface Factory {
    fun create(
      @BindsInstance application: BaseApplication, @BindsInstance @ApplicationContext context: Context
    ): AppComponent
  }
}