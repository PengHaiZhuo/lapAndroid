package com.phz.dev.ext

import androidx.lifecycle.viewModelScope
import com.phz.common.ext.logE
import com.phz.common.net.support.throwable.HttpException
import com.phz.common.net.support.throwable.HttpThrowableHandler
import com.phz.common.state.BaseViewModel
import com.phz.dev.net.data.bean.BaseResponse
import kotlinx.coroutines.*

/**
 * @author phz on 2021/4/15
 * @description
 */
suspend fun <T> BaseViewModel.requestNormal(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit, error: (HttpException) -> Unit = {}
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
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调
 * @param isShowDialog 是否显示加载框
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (HttpException) -> Unit = {},
    isShowDialog: Boolean = true
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) showLoading.postValue(isShowDialog)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            showLoading.postValue(false)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure { e ->
                //打印错误消息
                e.message?.logE()
                //失败回调
                error(HttpThrowableHandler.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            showLoading.postValue(false)
            //打印错误消息
            it.message?.logE()
            //失败回调
            error(HttpThrowableHandler.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
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
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}