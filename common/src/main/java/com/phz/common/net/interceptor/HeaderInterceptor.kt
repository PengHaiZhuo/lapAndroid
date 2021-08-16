package com.phz.common.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author phz
 * @description 头部参数拦截器示例
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("device", "Android").build()
        return chain.proceed(builder.build())
    }
}