package com.phz.dev.api

/**
 * @author phz
 * @description 数据状态封装
 */
sealed class DataState<out T> {
    object Empty : DataState<Nothing>() //返回数据为空
    object Loading : DataState<Nothing>()//加载状态

    data class Error(
        val message: String
    ) : DataState<Nothing>()

    data class Success<T>(
        val data: T
    ) : DataState<T>()

    fun read(): T = (this as Success<T>).data

    fun readSafely(): T? = if (this is Success<T>) read() else null
}