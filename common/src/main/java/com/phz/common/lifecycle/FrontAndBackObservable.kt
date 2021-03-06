package com.phz.common.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.phz.common.livedata.BooleanLiveData

/**
 * @author phz
 * @description 前后台观察者
 * @use add below in activity's onCreate or fragment's onViewCreated
 * FrontAndBackObservable.isForeground.observe(this){}//添加前后台状态变化观察者
 */
object FrontAndBackObservable: LifecycleObserver {
    var isForeground = BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private  fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }
}