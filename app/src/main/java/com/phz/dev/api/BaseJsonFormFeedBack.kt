package com.phz.dev.api

/**
 * @author phz
 * @description  json统一返回格式
 * 一般情况下，后台会定义一个统一返回格式，具体命名和含义与后台同学沟通
 */
data class BaseJsonFormFeedBack<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
) {
    fun isSuccess(): Boolean = errorCode == 0//后台规定请求成功返回0

    /**
     * 有后台是这么设计的，登陆之后每个请求都需要带上token做校验，也有不用这种方式使用cookie的。都可以实现单点登陆的功能
     */
    fun isTokenOverDue(): Boolean = errorCode == 400401 //后台规定token过期返回400401，需要重新获取

    fun getResponseData(): T = data

    fun getCode(): Int = errorCode

    fun getMsg(): String = errorMsg
}