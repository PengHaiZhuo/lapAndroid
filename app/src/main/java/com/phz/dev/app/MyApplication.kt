package com.phz.dev.app

import com.alibaba.android.arouter.launcher.ARouter
import com.phz.common.BaseApplication

/**
 * @author phz
 * @description 工程类
 */
class MyApplication :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }
}