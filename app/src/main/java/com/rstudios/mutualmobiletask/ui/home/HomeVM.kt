package com.rstudios.mutualmobiletask.ui.home

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.ArticleEntity
import com.example.data.local.entity.SourceXEntity
import com.rstudios.mutualmobiletask.BaseApplication
import com.example.data.remote.ApiResponse
import com.example.data.remote.model.NewsResponse
import com.example.data.remote.model.SourceResponse
import com.example.data.HomeRepository
import com.rstudios.mutualmobiletask.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeVM constructor(
  application: BaseApplication,
  private val homeRepository: HomeRepository
) : AndroidViewModel(application) {

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
        val response = homeRepository.getRemoteHeadLines()
        _headlines.value = handleRetrofitResponse(response)
      } else {
        _headlines.value = ApiResponse.Error(
          NewsResponse(homeRepository.getLocalHeadLines(), "Local", 20), "No Internet Connection"
        )
      }
    } catch (t: Throwable) {
      _headlines.value = ApiResponse.Error(null, "Error")
    }
  }

  fun getSources() = viewModelScope.launch {
    _sources.value = ApiResponse.Loading
    try {
      if (Constants.hasInternetConnection(getApplication())) {
        val response = homeRepository.getRemoteSources()
        _sources.value = handleRetrofitResponse(response)
      } else {
        _sources.value = ApiResponse.Error(
          SourceResponse(homeRepository.getLocalSources(), "Local"), "No Internet Connection"
        )
      }
    } catch (t: Throwable) {
      _headlines.value = ApiResponse.Error(null, "Error")
    }
  }

  fun insertArticles(list: List<ArticleEntity>) {
    viewModelScope.launch { homeRepository.putLocalHeadLines(list) }
  }

  fun insertSources(list: List<SourceXEntity>) {
    viewModelScope.launch { homeRepository.putLocalSources(list) }
  }

  fun setManager(selected: Int) {
    _selected.value = selected
  }

  private fun <T> handleRetrofitResponse(response: Response<T>): ApiResponse<T> {
    if (response.isSuccessful) {
      Log.i("response",response.body().toString())
      response.body()?.let { return ApiResponse.Success<T>(it, "Successful") }
    }
    return ApiResponse.Error(null, response.message())
  }
}