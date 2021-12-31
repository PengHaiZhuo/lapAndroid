package com.phz.dev.api

/**
 * @author phz
 * @description 一般返回结果封装
 */
sealed class Response<T>(
    private val data: T? = null,
    private val errorMessage: String = ""
) {
    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failed(errorMessage: String = "failed") = Failed<T>(errorMessage)
    }

    fun isSuccess() = this is Success
    fun isFailed() = this is Failed

    fun read() = data!!
    fun readSafely(): T? = if (this is Success<T>) read() else null
    fun errorMessage() = errorMessage

    /* 当前状态，可以根据状态展示页面 */
    fun toDataState() = if(isSuccess()) DataState.Success(read()) else DataState.Error(errorMessage())

    class Success<T> internal constructor(data: T) : Response<T>(data = data)
    class Failed<T> internal constructor(errorMessage: String) :
        Response<T>(errorMessage = errorMessage)
}
