package com.ryancode.jokeapp.mvvm.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkErrorHandler {
    fun handleError(exception: Throwable): String {
        return when (exception) {
            is HttpException -> "Server error: ${exception.code()}"
            is SocketTimeoutException -> "Connection timed out"
            is IOException -> "Network error, please check your connection"
            else -> "Unknown error"
        }
    }
}