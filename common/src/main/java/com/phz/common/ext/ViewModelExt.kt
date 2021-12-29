package com.phz.common.ext

import androidx.lifecycle.ViewModel
import com.phz.common.net.support.data.BaseJsonFormFeedBack
import com.phz.common.net.support.throwable.HttpException
import com.phz.common.net.support.throwable.HttpThrowableHandler
import kotlinx.coroutines.*

/**
 * 配合retrofit进行请求
 * @param block 请求体方法，用suspend关键字修饰的api方法
 * @param success 成功回调
 * @param error 失败回调
 */
suspend fun <T> ViewModel.request(
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
    response: BaseJsonFormFeedBack<T?>,
    success: suspend CoroutineScope.(T?) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            response.isTokenOverDue() -> {
                //token过期处理
            }
            else -> {
                throw HttpException(
                    response.getCode(),
                    response.getMsg()
                )
            }
        }
    }
}