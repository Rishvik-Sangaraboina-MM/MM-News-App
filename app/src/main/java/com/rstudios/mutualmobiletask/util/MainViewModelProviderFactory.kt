package com.rstudios.mutualmobiletask.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rstudios.mutualmobiletask.repository.MainRepository
import com.rstudios.mutualmobiletask.viewmodel.MainViewModel

class MainViewModelProviderFactory(val application: Application,val mainRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application,mainRepository) as T
    }
}