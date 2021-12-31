package com.phz.dev.api

import com.phz.dev.data.model.UserBean

/**
 * @author phz on 2021/12/29
 * @description 带状态的api，实现见其实现类
 * 这里有个规范，参考书籍《Practical API Design》：返回的bean对象不为null没有就返回默认初始化对象，集合可以为空但是集合对象不能为null
 * 如果后端有问题，请你说服他(๑•́ ₃•̀๑)
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