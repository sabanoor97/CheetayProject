package com.faiz.baseproject.network

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val code: Int, val message: String) : DataState<T>()

    class Loading<T> : DataState<T>()

    data class ApiError<T>(val message: String) : DataState.PaymentError<T>()
    open class PaymentError<T> : DataState<T>()

    companion object {
        fun <T> success(data: T) = Success<T>(data)
        fun <T> error(code: Int, message: String) = Error<T>(code, message)
    }
}
