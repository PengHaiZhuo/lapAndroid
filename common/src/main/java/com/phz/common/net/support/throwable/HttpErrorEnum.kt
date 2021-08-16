package com.phz.common.net.support.throwable

/**
 * @author phz
 * @description http错误枚举类
 */
enum class NetError(private val code:Int, private val msg:String) {
    /**
     * 位置错误
     */
    UNKNOWN(1000,"未知错误，请稍后重试"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误，请稍后重试"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络(协议)错误，请稍后重试"),
    /**
     * 主机不可达 DNF解析失败
     */
    NO_HOST_ERROR(1003,"DNF解析失败，请稍后重试"),
    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错，请稍后重试"),
    /**
     * 连接失败
     */
    CONNECT_ERROR(1005,"连接失败，请稍后重试"),
    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "网络连接超时，请稍后重试");

    fun getValue(): String {
        return msg
    }

    fun getKey(): Int {
        return code
    }
}