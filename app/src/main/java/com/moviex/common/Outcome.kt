package com.moviex.common

sealed class Outcome<T> {

    data class Success<T>(var data: T) : Outcome<T>()
    data class Failure<T>(val e: Throwable) : Outcome<T>()
    data class Progress<T>(var loading: Boolean) : Outcome<T>()

    companion object {

        fun <T> success(data: T): Outcome<T> = Success(data)

        fun <T> failure(e: Throwable): Outcome<T> = Failure(e)

        fun <T> loading(isLoading: Boolean): Outcome<T> = Progress(isLoading)
    }
}