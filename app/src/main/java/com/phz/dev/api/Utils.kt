package com.phz.dev.api

import com.phz.common.ext.logE
import com.phz.common.net.support.throwable.HttpException
import com.phz.common.net.support.throwable.HttpThrowableHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * @author phz on 2021/12/31
 * @description 网络请求工具类
 */
object Utils {
    /**
     * 在任意协程作用域或者suspend修饰的方法中调用可以执行此方法进行api请求
     * @param block 请求体方法，用suspend关键字修饰的api方法
     * @param success 成功回调
     * @param error 失败回调
     */
    suspend fun <T> request(
        block: suspend () -> BaseJsonFormFeedBack<T?>,
        success: (T?) -> Unit, error: (HttpException) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            runCatching {
                //请求体
                block()
            }.onSuccess {
                runCatching {
                    //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                    executeResponse(it) { t ->
                        withContext(Dispatchers.Main) {
                            success(t)
                        }
                    }
                }.onFailure { e ->
                    withContext(Dispatchers.Main) {
                        //打印错误消息
                        e.message?.logE()
                        //失败回调
                        error(HttpThrowableHandler.handleException(e))
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    //打印错误消息
                    it.message?.logE()
                    //失败回调
                    error(HttpThrowableHandler.handleException(it))
                }
            }
        }
    }

    /**
     * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
     */
    suspend fun <T> executeResponse(
        response: BaseJsonFormFeedBack<T>,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            when {
                response.isSuccess() -> {
                    success(response.data)
                }
                response.isTokenOverDue() -> {
                    //token过期处理
                }
                else -> {
                    throw HttpException(
                        response.code,
                        response.msg
                    )
                }
            }
        }
    }
}