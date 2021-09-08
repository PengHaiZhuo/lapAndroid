package com.phz.dev.net.interceptor

import com.phz.dev.app.Constants
import com.tencent.mmkv.MMKV
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
        val mToken = MMKV.defaultMMKV().decodeString(Constants.TOKEN)//获取token字段
        if (!mToken.isNullOrEmpty()) {
            builder.addHeader("Authorization", mToken).build()//防止401 authentication错误
        }
        val realm = MMKV.defaultMMKV().decodeString(Constants.DOMAIN_PREFIX)//获取2级域名
        return if (realm.isNullOrEmpty()) {
            chain.proceed(builder.build())
        } else {
            builder.addHeader("realm", realm)//添加头部
            chain.proceed(builder.build())
        }
    }
}