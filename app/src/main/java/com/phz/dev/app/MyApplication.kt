package com.phz.dev.app

import android.content.Context
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.phz.common.BaseApplication
import com.phz.dev.BuildConfig
import com.tencent.bugly.Bugly
import com.tencent.mmkv.MMKV

/**
 * @author phz
 * @description 工程类
 */
class MyApplication :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        //百度地图SDK初始化
        SDKInitializer.initialize(this)
        //设置坐标类型
        SDKInitializer.setCoordType(CoordType.BD09LL)
        //初始化，并指定默认存储路径，通过Context获取的私有路径不需要授予权限
        MMKV.initialize(this)
        //异常上报
        Bugly.init(this, "4d5d3f6f50", BuildConfig.DEBUG);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}