package com.phz.common.net.manager

import kotlinx.coroutines.flow.MutableStateFlow


/**
 *         如何使用
 *         1.在页面对应生命周期方法内添加注册广播和注销广播
 *         private lateinit var mNetStateReceiver: NetStateReceiver
 *         //注册网络变化监听广播
 *         mNetStateReceiver = NetStateReceiver()
 *         registerReceiver(mNetStateReceiver,IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))//虽然被标记为过时，但是动态注册还是能用的
 *
 *         //解除注册广播
 *         unregisterReceiver(mNetStateReceiver)
 *
 *         2.添加状态变化观察者
 *         lifecycleScope.launch {
 *              //添加网络状态变化观察者
 *              NetStateManager.instance.isNetAvailable.collect {
 *              showSnackShort(if (it){"有网"}else{"断网"})
 *              }
 *         }
 */
class NetStateManager private constructor() {
    //默认有网络
    val isNetAvailable= MutableStateFlow(true)

    companion object {
        val instance: NetStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetStateManager()
        }
    }

}