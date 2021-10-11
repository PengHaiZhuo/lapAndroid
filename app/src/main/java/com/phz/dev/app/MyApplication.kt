package com.phz.dev.app

import android.content.Context
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.phz.common.BaseApplication

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
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}