package com.phz.dev.di

import com.phz.dev.api.WanAndroidApi
import com.phz.dev.repo.WanAndroidRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author phz on 2021/12/31
 * @description 仓库模块
 */
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideWanAndroidRepo(
        wanAndroidApi: WanAndroidApi
    )=WanAndroidRepo(wanAndroidApi)
}