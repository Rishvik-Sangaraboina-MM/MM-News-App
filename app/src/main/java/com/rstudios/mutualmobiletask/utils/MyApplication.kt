package com.rstudios.mutualmobiletask.utils

import android.app.Application
import com.rstudios.mutualmobiletask.injection.component.AppComponent
import com.rstudios.mutualmobiletask.injection.component.DaggerAppComponent

class MyApplication : Application(){

  val appComponent : AppComponent by lazy {
    DaggerAppComponent.factory().create(this,applicationContext)
  }

}