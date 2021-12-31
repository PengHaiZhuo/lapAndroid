package com.phz.dev.repo

import com.phz.dev.api.Response
import com.phz.dev.api.WanAndroidApi
import com.phz.dev.data.model.UserBean

/**
 * @author phz on 2021/12/28
 * @description WanAndroidApi仓库类
 */
class WanAndroidRepo(private val wanAndroidApi: WanAndroidApi) {
    suspend fun login(username: String, password: String):Response<UserBean> =
        wanAndroidApi.login(username,password)
}