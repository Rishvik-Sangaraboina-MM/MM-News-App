package com.rstudios.mutualmobiletask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rstudios.mutualmobiletask.BaseApplication
import com.example.data.HomeRepository
import com.rstudios.mutualmobiletask.ui.home.HomeVM
import javax.inject.Inject

class HomeVMProviderFactory @Inject constructor(
  val application: BaseApplication,
  private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return HomeVM(application, homeRepository) as T
  }
}