package com.example.data.remote

sealed class ApiResponse<out T> {

  data class Success<T>(
    val data: T?,
    val message: String?
  ) : ApiResponse<T>()

  object Loading : ApiResponse<Nothing>()

  data class Error<T>(
    val data: T?,
    val message: String?
  ) : ApiResponse<T>()

}