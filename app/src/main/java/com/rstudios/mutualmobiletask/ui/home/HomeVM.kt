package com.rstudios.mutualmobiletask.ui.home

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rstudios.mutualmobiletask.BaseApplication
import com.rstudios.mutualmobiletask.api.ApiResponse
import com.rstudios.mutualmobiletask.model.NewsResponse
import com.rstudios.mutualmobiletask.model.SourceResponse
import com.rstudios.mutualmobiletask.repository.MainRepository
import com.rstudios.mutualmobiletask.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class HomeVM @Inject constructor(
  application: BaseApplication,
  val mainRepository: MainRepository
) : AndroidViewModel(application) {

  init {
    Log.i("HomeVM","Initialized")
  }
  private val _headlines: MutableLiveData<ApiResponse<NewsResponse>> = MutableLiveData()
  val headlines: LiveData<ApiResponse<NewsResponse>> = _headlines

  private val _sources: MutableLiveData<ApiResponse<SourceResponse>> = MutableLiveData()
  val sources: LiveData<ApiResponse<SourceResponse>> = _sources

  private val _selected: MutableLiveData<Int> = MutableLiveData()
  val selected: LiveData<Int> = _selected

  init {
    setManager(0)
    getHeadlines()
    getSources()
  }

  fun getHeadlines() = viewModelScope.launch {
    _headlines.value = ApiResponse.Loading
    try {
      if (Constants.hasInternetConnection(getApplication())) {
        val response = mainRepository.getHeadLines()
        _headlines.value = handleRetrofitResponse(response)
      } else {
        _headlines.value = ApiResponse.Error(null, "No Internet Connection")
      }
    } catch (t: Throwable) {
      _headlines.value = ApiResponse.Error(null, "Error")
    }
  }

  fun getSources() = viewModelScope.launch {
    _sources.value = ApiResponse.Loading
    try {
      if (Constants.hasInternetConnection(getApplication())) {
        val response = mainRepository.getSources()
        _sources.value = handleRetrofitResponse(response)
      } else {
        _sources.value = ApiResponse.Error(null, "No Internet Connection")
      }
    } catch (t: Throwable) {
      _headlines.value = ApiResponse.Error(null, "Error")
    }
  }

  fun setManager(selected: Int) {
    _selected.value = selected
  }

  private fun <T> handleRetrofitResponse(response: Response<T>): ApiResponse<T> {
    if (response.isSuccessful)
      response.body()?.let { return ApiResponse.Success<T>(it,"Successful") }
    return ApiResponse.Error(null, response.message())
  }
}