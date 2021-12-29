package com.phz.dev.api

import com.phz.common.ext.executeResponse
import com.phz.common.ext.logE
import com.phz.common.net.support.throwable.HttpThrowableHandler
import com.phz.dev.api.service.WanAndroidApiService
import com.phz.dev.data.model.UserBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author phz on 2021/12/29
 * @description
 */
class WanAndroidApiImpl(private val apiService:WanAndroidApiService):WanAndroidApi {
    override suspend fun login(username: String, password: String): Response<UserBean> {
        withContext(Dispatchers.IO) {
            runCatching {
                apiService.login(username,password)
            }.onSuccess {
                runCatching {
                    //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                    executeResponse(it) { t ->
                        withContext(Dispatchers.Main) {
                            Response.success(t)
                        }
                    }
                }.onFailure { e ->
                    withContext(Dispatchers.Main) {
                        //打印错误消息
                        e.message?.logE()
                        val ex=HttpThrowableHandler.handleException(e)
                        //失败回调
                        Response.failed<UserBean>(ex.errorMsg)
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    //打印错误消息
                    it.message?.logE()
                    val ex=HttpThrowableHandler.handleException(it)
                    //失败回调
                    Response.failed<UserBean>(ex.errorMsg)
                }
            }
        }
    }

    override suspend fun logout(): Response<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        username: String,
        password: String,
        confirm: String
    ): Response<UserBean> {
        TODO("Not yet implemented")
    }
}