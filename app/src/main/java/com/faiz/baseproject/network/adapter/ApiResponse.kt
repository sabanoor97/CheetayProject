package com.faiz.baseproject.network.adapter

import retrofit2.Response

sealed interface ApiResponse<out T> {

    /**
     * API Success response class from retrofit.
     *
     * [data] is optional. (There are responses without data)
     */
    data class ApiSuccessResponse<T>(val response: Response<T>) : ApiResponse<T> {
        val data: T? = response.body()
    }

    /**
     * Failure class represents two types of Failure:
     * 1) ### Error response e.g. server error
     * 2) ### Exception response e.g. network connection error
     */
    sealed class ApiFailureResponse<T> {
        data class Error<T>(val response: Response<T>) : ApiResponse<T>

        data class ApiSuccessRangeErrorResponse<T>(val response: Response<T>) : ApiResponse<T> {
            val data: T? = response.body()
        }

        data class Exception<T>(val exception: Throwable) : ApiResponse<T> {
            val message: String? = exception.localizedMessage
        }
    }

    companion object {

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function. Only receives [Throwable] as an argument.
         */
        fun <T> exception(ex: Throwable) = ApiFailureResponse.Exception<T>(ex)

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function.
         */
        fun <T> error(response: Response<T>) = ApiFailureResponse.Error<T>(response)

        /**
         * ApiResponse Factory.
         *
         * [create] Create [ApiResponse] from [retrofit2.Response] returning from the block.
         * If [retrofit2.Response] has no errors, it creates [ApiResponse.ApiSuccessResponse].
         * If [retrofit2.Response] has errors, it creates [ApiResponse.ApiFailureResponse.Error].
         * If [retrofit2.Response] has occurred exceptions, it creates [ApiResponse.ApiFailureResponse.Exception].
         */
        fun <T> create(
            successCodeRange: IntRange = 200..204,
            successErrorCodeRange: IntRange = 205..299,
            response: Response<T>
        ): ApiResponse<T> = try {
            if (response.code() in successCodeRange) {
                ApiSuccessResponse(response)
            } else if (response.code() in successErrorCodeRange) {
                ApiFailureResponse.ApiSuccessRangeErrorResponse(response)
            } else {
                ApiFailureResponse.Error(response)
            }
        } catch (ex: Exception) {
            ApiFailureResponse.Exception(ex)
        }
    }
}