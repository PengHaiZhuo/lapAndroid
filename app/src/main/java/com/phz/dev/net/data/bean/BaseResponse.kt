package com.phz.dev.net.data.bean

import com.phz.common.net.support.data.BaseJsonResponse

/**
 * @author phz
 * @description http请求基本feedback
 */
data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val errorMsg: String,
    val data: T
) : BaseJsonResponse<T>() {

    override fun isSuccess(): Boolean {
        return status == 200
    }

    fun isTokenOverDue(): Boolean {
        //token过期的情况 判断条件根据后台返回code决定
        return (status == 40301 || status == 40101)
    }

    override fun getResponseData(): T {
        return data
    }

    override fun getResponseCode(): Int {
        return status
    }

    override fun getResponseMsg(): String {
        return message
    }

    fun getErrorMessage(): String {
        return errorMsg
    }

}