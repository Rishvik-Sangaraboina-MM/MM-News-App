package com.rstudios.mutualmobiletask.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rstudios.mutualmobiletask.api.ApiResponse
import com.rstudios.mutualmobiletask.model.NewsResponse
import com.rstudios.mutualmobiletask.repository.SearchRepository
import com.rstudios.mutualmobiletask.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchVM constructor(
  application: Application,
  val searchRepository: SearchRepository
) : AndroidViewModel(application) {

  private val _search: MutableLiveData<ApiResponse<NewsResponse>> = MutableLiveData()
  val search: LiveData<ApiResponse<NewsResponse>> = _search

  fun getEverything(keyword: String) = viewModelScope.launch {
    _search.value = ApiResponse.loading(null)
    try {
      if (Constants.hasInternetConnection(getApplication())) {
        val response = searchRepository.getEverything(keyword)
        _search.value = handleRetrofitResponse<NewsResponse>(response)
      } else {
        _search.value = ApiResponse.error(null, "No Internet Connection")
      }
    } catch (t: Throwable) {
      when (t) {
        is IOException -> _search.value = ApiResponse.error(null, "Network Failure")
        else -> _search.value = ApiResponse.error(null, "Conversion Error")
      }
    }
  }

  private fun <T> handleRetrofitResponse(response: Response<T>): ApiResponse<T> {
    if (response.isSuccessful)
      response.body()?.let { return ApiResponse.success(it) }
    return ApiResponse.error(null, response.message())
  }
}