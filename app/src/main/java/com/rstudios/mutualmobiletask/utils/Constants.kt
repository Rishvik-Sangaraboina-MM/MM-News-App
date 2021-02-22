package com.rstudios.mutualmobiletask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Constants {
  companion object {
    val BASE_URL = "https://newsapi.org"
    val HEADLINES_URL = "v2/top-headlines"
    val SOURCE_URL = "v2/sources"
    val EVERYTHING_URL = "v2/everything"
    val API_KEY = "06ea1d1712274203b4985c97762f4456"

    fun hasInternetConnection(application: MyApplication): Boolean {
      val connectivityManager = application.getSystemService(
        Context.CONNECTIVITY_SERVICE
      ) as ConnectivityManager
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
          else -> false
        }
      } else {
        connectivityManager.activeNetworkInfo?.run {
          return when (type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            ConnectivityManager.TYPE_ETHERNET -> true
            else -> false
          }
        }
      }
      return false
    }
  }
}