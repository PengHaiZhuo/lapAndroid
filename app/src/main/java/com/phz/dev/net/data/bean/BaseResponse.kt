package com.phz.dev.net.data.bean

import com.phz.common.net.support.data.BaseJsonResponse
import com.phz.dev.net.ApiService

/**
 * @author phz
 * @description http请求基本feedback
 */
data class BaseResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
) : BaseJsonResponse<T>() {

    override fun isSuccess(): Boolean {
        //玩安卓api请求成功返回0
        return errorCode == 0
    }

    fun isTokenOverDue(): Boolean {
        //登录成功获取有时效的token字段，本地缓存，后面的请求附带这个参数
        //服务器做校验，当token过期返回一个过期的code，重新登录获取新token
        //不使用这种方案的话，可以使用cookie，方式差不多。现在都网络请求框架基本都可以设置，比如okhttp可以设置cookieJar
        return (errorCode == ApiService.TOKEN_OVERDUE)
    }

    override fun getResponseData(): T {
        return data
    }

    override fun getResponseCode(): Int {
        return errorCode
    }

    override fun getResponseMsg(): String {
        return errorMsg
    }

}