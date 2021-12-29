package com.phz.dev.di

import com.phz.common.appContext
import com.phz.common.net.interceptor.CacheInterceptor
import com.phz.common.net.interceptor.HeaderInterceptor
import com.phz.common.net.interceptor.log.LogInterceptor
import com.phz.common.net.persistentcookiejar.PersistentCookieJar
import com.phz.common.net.persistentcookiejar.cache.SetCookieCache
import com.phz.common.net.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.phz.dev.api.WanAndroidApi
import com.phz.dev.api.WanAndroidApiImpl
import com.phz.dev.api.service.WanAndroidApiService
import com.phz.dev.net.ApiService
import com.phz.dev.net.authenticator.MyAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @author phz on 2021/12/23
 * @description 网络模块
 */
@Module
//InstallIn标签作用是告知 Hilt 每个模块将用在或安装在哪个 Android 类
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private const val TIME_OUT = 10L//超时时间
    private const val SERVER_ADDRESS = "https://www.wanandroid.com"

    @Provides
    @Singleton
    fun provideCookieJar(): PersistentCookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))

    @Provides
    @WanAndroidOkhttpClient
    @Singleton
    fun provideWanAndroidOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(File(appContext.cacheDir, "okhttp"), 10 * 1024 * 1024))//设置缓存配置 路径和大小
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .cookieJar(provideCookieJar())
        .addInterceptor(CacheInterceptor())//设置缓存拦截器
        .addInterceptor(HeaderInterceptor())//设置自定义头部拦截器
        .addInterceptor(LogInterceptor())//设置日志拦截器
        /*.addInterceptor(HttpLoggingInterceptor())*/
        .authenticator(MyAuthenticator())//设置身份验证处理器
        .build()

    @Provides
    @WanAndroidRetrofit
    @Singleton
    fun provideWanAndroidRetrofit(@WanAndroidOkhttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(SERVER_ADDRESS)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWanAndroidApiService(@WanAndroidRetrofit retrofit: Retrofit): WanAndroidApiService =
        retrofit.create(WanAndroidApiService::class.java)

    @Provides
    @Singleton
    fun provideWanAndroidApi(wanAndroidApiService: WanAndroidApiService):WanAndroidApi=WanAndroidApiImpl(wanAndroidApiService)
}

@Qualifier
annotation class WanAndroidRetrofit

@Qualifier
annotation class WanAndroidOkhttpClient