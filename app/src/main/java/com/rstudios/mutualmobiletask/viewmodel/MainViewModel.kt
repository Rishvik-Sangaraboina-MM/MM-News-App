package com.rstudios.mutualmobiletask.viewmodel

import android.app.Application
import androidx.lifecycle.*

import com.rstudios.mutualmobiletask.api.ApiResponse
import com.rstudios.mutualmobiletask.model.NewsResponse
import com.rstudios.mutualmobiletask.model.SourceResponse
import com.rstudios.mutualmobiletask.repository.MainRepository
import com.rstudios.mutualmobiletask.util.Constants
import com.rstudios.mutualmobiletask.util.MyApplication
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(application: Application,private val mainRepository: MainRepository) : AndroidViewModel(application) {
    private val _headlines:MutableLiveData<ApiResponse<NewsResponse>> = MutableLiveData()
    val headlines : LiveData<ApiResponse<NewsResponse>> = _headlines

    private val _sources:MutableLiveData<ApiResponse<SourceResponse>> = MutableLiveData()
    val sources : LiveData<ApiResponse<SourceResponse>> = _sources

    private val _selected : MutableLiveData<Int> = MutableLiveData()
    val selected : LiveData<Int> = _selected

    init {
        setManager(0)
        getHeadlines()
        getSources()
    }

    fun getHeadlines() = viewModelScope.launch {
        _headlines.value = ApiResponse.loading(null)
        try {
            if(Constants.hasInternetConnection(getApplication())) {
                val response = mainRepository.getHeadLines()
                _headlines.value = handleRetrofitResponse<NewsResponse>(response)
            } else {
                _headlines.value = ApiResponse.error(null,"No Internet Connection")
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _headlines.value = ApiResponse.error(null,"Network Failure")
                else -> _headlines.value = ApiResponse.error(null,"Conversion Error")
            }
        }
    }

    fun getSources() = viewModelScope.launch {
        _sources.value = ApiResponse.loading(null)
        try {
            if(Constants.hasInternetConnection(getApplication())) {
                val response = mainRepository.getSources()
                _sources.value = handleRetrofitResponse<SourceResponse>(response)
            } else {
                _sources.value = ApiResponse.error(null,"No Internet Connection")
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _sources.value = ApiResponse.error(null,"Network Failure")
                else -> _sources.value = ApiResponse.error(null,"Conversion Error")
            }
        }
    }

    fun setManager(selected : Int){
        _selected.value=selected
    }

    private fun <T> handleRetrofitResponse(response: Response<T>) : ApiResponse<T>{
        if(response.isSuccessful)
            response.body()?.let { return ApiResponse.success(it) }
        return ApiResponse.error(null,response.message())
    }

}