package com.phz.dev.net.interceptor

import com.phz.dev.util.PersistenceUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author phz
 * @description 头部参数拦截器
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("device", "Android").build()
        val mToken = PersistenceUtil.getToken()//获取token字段
        if (mToken.isNotEmpty()) {
            builder.addHeader("Authorization", mToken).build()//防止401 authentication错误
        }
        return  chain.proceed(builder.build())
    }
}