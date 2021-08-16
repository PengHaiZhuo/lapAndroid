package com.phz.common.net.interceptor

import com.phz.common.appContext
import com.phz.common.util.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author hgj
 * @description 缓存拦截器
 * @param day 缓存天数 默认7天
 */
class CacheInterceptor(var day: Int = 7) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //请求设置
        var request = chain.request()
        if (!NetWorkUtils.isNetConnected(appContext)) {
            //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
            //有网络时则根据缓存时长来决定是否发出请求。
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }

        //应答设置
        val response = chain.proceed(request)
        if (!NetWorkUtils.isNetConnected(appContext)) {
            //缓存时间1小时
            val maxAge = 60 * 60
            response.newBuilder()
                .removeHeader("Pragma")//移除不适用缓存Header
                .header("Cache-Control", "public, max-age=$maxAge")//添加缓存控制Header
                .build()
        } else {
            //缓存7天
            val maxStale = 60 * 60 * 24 * day
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return response
    }
}