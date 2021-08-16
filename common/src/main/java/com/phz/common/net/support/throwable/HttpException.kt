package com.phz.common.net.support.throwable

import java.lang.Exception

/**
 * @author phz
 * @description 自定义http异常类
 */
class HttpException : Exception {
    var errorMsg: String //错误消息
    var errorCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    constructor(errCode: Int, error: String?, errorLog: String? = "") : super(error) {
        this.errorMsg = error ?: "请求失败，请稍后再试"
        this.errorCode = errCode
        this.errorLog = errorLog?:this.errorMsg
    }

    constructor(error: NetError,e: Throwable?) {
        errorCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
    }
}