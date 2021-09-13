package com.phz.dev.app

import android.content.Context
import com.phz.common.BaseApplication

/**
 * @author phz
 * @description 工程类
 */
class MyApplication :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}