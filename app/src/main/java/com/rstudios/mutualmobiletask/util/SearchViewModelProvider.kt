package com.rstudios.mutualmobiletask.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rstudios.mutualmobiletask.repository.SearchRepository
import com.rstudios.mutualmobiletask.viewmodel.SearchViewModel

class SearchViewModelProvider(val application: Application,val searchRepository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(application,searchRepository) as T
    }
}