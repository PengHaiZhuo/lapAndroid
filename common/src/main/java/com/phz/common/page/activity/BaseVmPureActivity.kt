package com.phz.common.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phz.common.ext.getVmClazz
import com.phz.common.lifecycle.FrontAndBackObservable
import com.phz.common.net.manager.NetState
import com.phz.common.net.manager.NetStateManager
import com.phz.common.state.BaseViewModel

/**
 * @author phz
 * @description Activity基类 VM纯净版,不使用activity_base作为父布局
 */
abstract class BaseVmPureActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        initView(savedInstanceState)
        NetStateManager.instance.mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
        FrontAndBackObservable.isForeground.observe(this) {
            onFrontAndBackChanged(it)
        }
        initData()
    }

    abstract fun getLayoutId(): Int

    /**
     * 可以在此方法内加载数据，创建数据源观察者，配合setOnclick()拓展函数设置点击事件
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 网络变化监听 子类重写即可获取网络状态监听回调
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 前后台监听 子类重写即可获取前后台状态监听回调
     */
    open fun onFrontAndBackChanged(boolean: Boolean) {}
}