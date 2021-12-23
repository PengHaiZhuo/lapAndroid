package com.phz.common

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.phz.common.ext.getAppName
import com.phz.common.lifecycle.ActivityLifeCircleCallBackMine
import com.phz.common.lifecycle.FrontAndBackObservable
import com.phz.common.net.manager.NetStateReceiver


//全局上下文
val appContext: BaseApplication by lazy { BaseApplication.instance }
val appLogTag: String by lazy { BaseApplication.appName }

/**
 * 工程基类
 */
open abstract class BaseApplication : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore
    private lateinit var mNetStateReceiver: NetStateReceiver
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        lateinit var instance: BaseApplication
        lateinit var appName: String
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appName = this.getAppName()
        mAppViewModelStore = ViewModelStore()
        //添加前后台监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(FrontAndBackObservable)
        //注册活动生命周期回调
        registerActivityLifecycleCallbacks(ActivityLifeCircleCallBackMine)
        //注册网络变化监听广播
        mNetStateReceiver= NetStateReceiver()
        registerReceiver(mNetStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))//虽然被标记为过时，但是手动注册还是能用的
//        unregisterReceiver(mNetStateReceiver)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}
