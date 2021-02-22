package com.rstudios.mutualmobiletask.api

data class ApiResponse<T>(
  val status: Status,
  val data: T?,
  val message: String?
) {

  companion object {
    fun <T> success(data: T?) = ApiResponse(Status.SUCCESS, data, null)

    fun <T> loading(data: T?) = ApiResponse(Status.LOADING, data, null)

    fun <T> error(
      data: T?,
      message: String?
    ) = ApiResponse(Status.ERROR, data, message)
  }
}

enum class Status {
  SUCCESS,
  ERROR,
  LOADING
}