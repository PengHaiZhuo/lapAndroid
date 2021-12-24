package com.phz.dev.di

import com.phz.common.appContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @author phz on 2021/12/23
 * @description
 */
@Module
//InstallIn标签作用是告知 Hilt 每个模块将用在或安装在哪个 Android 类
@InstallIn(SingletonComponent::class)
object NetWorkModule{
    //超时时间
    private const val TIME_OUT=10L

    @Provides
    @Singleton
    fun provideWanAndroidOkhttpClient():OkHttpClient=OkHttpClient.Builder()
        .cache(Cache(File(appContext.cacheDir, "okhttp"), 10 * 1024 * 1024))
        .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
        .readTimeout(TIME_OUT,TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
        .build()
}

@Qualifier
annotation class WanAndroidRetrofit

@Qualifier
annotation class WanAndroidOkhttpClient