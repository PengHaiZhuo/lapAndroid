package com.phz.dev.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author phz
 * @description  json统一返回格式
 * 一般情况下，后台会定义一个统一返回格式，具体命名和含义与后台同学沟通
 */
data class BaseJsonFormFeedBack<out T>(
    /*此json解析字串在本地叫code，远程可能叫code也可能叫errorCode*/
    @SerializedName(value = "code", alternate = ["errorCode"])
    val code: Int,
    @SerializedName(value = "msg", alternate = ["errorMsg"])
    val msg: String,
    val data: T
) :Serializable{
    fun isSuccess(): Boolean = code == 0//后台规定请求成功返回0

    /**
     * 有后台是这么设计的，登陆之后每个请求都需要带上token做校验，也有不用这种方式使用cookie的。都可以实现单点登陆的功能
     */
    fun isTokenOverDue(): Boolean = code == 400401 //后台规定token过期返回400401，需要重新获取
}