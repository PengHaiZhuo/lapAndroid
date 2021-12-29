package com.phz.dev.api

import com.phz.dev.data.model.UserBean

/**
 * @author phz on 2021/12/29
 * @description 带状态的api，实现见其实现类
 */
interface WanAndroidApi {
    /**
     * 登陆
     */
    suspend fun login(username: String, password: String): Response<UserBean>

    /**
     * 登出
     */
    suspend fun logout():Response<Boolean>

    /**
     * 注册
     */
    suspend fun register(username: String,password: String,confirm:String):Response<UserBean>
}