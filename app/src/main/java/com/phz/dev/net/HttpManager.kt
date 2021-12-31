package com.phz.dev.net

import com.google.gson.GsonBuilder
import com.phz.common.appContext
import com.phz.common.net.HttpCommonManager
import com.phz.common.net.interceptor.CacheInterceptor
import com.phz.common.net.interceptor.HeaderInterceptor
import com.phz.common.net.interceptor.log.LogInterceptor
import com.phz.common.net.persistentcookiejar.PersistentCookieJar
import com.phz.common.net.persistentcookiejar.cache.SetCookieCache
import com.phz.common.net.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.phz.dev.net.authenticator.MyAuthenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author phz
 * @description http请求管理类
 */
const val SERVER_ADDRESS="https://www.wanandroid.com"   /*玩安卓baseurl*/

/*双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口*/
val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpManager.INSTANCE.getApi(ApiService::class.java, SERVER_ADDRESS)
}

class HttpManager : HttpCommonManager() {

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }

    companion object {
        //单例
        val INSTANCE: HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpManager()
        }
    }


    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "okhttp"), 10 * 1024 * 1024))
            //添加Cookies自动持久化,去掉这行默认NoCookies
            cookieJar(cookieJar)
            //添加cache拦截器
            addInterceptor(CacheInterceptor())
            //添加Header拦截器
            addInterceptor(HeaderInterceptor())
            //添加Log拦截器
            addInterceptor(LogInterceptor())
            //也可以使用okhttp自带的HttpLoggingInterceptor，不过有时候打印不全
//            addInterceptor(HttpLoggingInterceptor())
            //处理身份验证超时
            authenticator(MyAuthenticator())
            //设置超时时间
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }
        return builder
    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }

    }
}
