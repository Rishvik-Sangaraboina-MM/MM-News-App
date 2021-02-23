package com.rstudios.mutualmobiletask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rstudios.mutualmobiletask.BaseApplication
import com.rstudios.mutualmobiletask.repository.MainRepository
import com.rstudios.mutualmobiletask.ui.home.HomeVM
import javax.inject.Inject

class HomeVMProviderFactory @Inject constructor(
  val application: BaseApplication,
  val mainRepository: MainRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return HomeVM(application, mainRepository) as T
  }
}