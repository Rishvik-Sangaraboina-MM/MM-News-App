package com.rstudios.mutualmobiletask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rstudios.mutualmobiletask.repository.SearchRepository
import com.rstudios.mutualmobiletask.ui.search.SearchVM
import javax.inject.Inject

class SearchVMProviderFactory @Inject constructor(
  val application: MyApplication,
  val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return SearchVM(application, searchRepository) as T
  }
}