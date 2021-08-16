package com.phz.common.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.phz.common.ext.logD
import com.phz.common.util.ActivityManagerKtx

/**
 * @author phz
 * @description
 */
object ActivityLifeCircleCallBackMine : Application.ActivityLifecycleCallbacks {
    private const val TAG = "ActivityLifecycleCallbacks"
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        "onActivityCreated : ${activity.localClassName}".logD(tag = TAG)
        ActivityManagerKtx.pushActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".logD(tag = TAG)
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".logD(tag = TAG)
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".logD(tag = TAG)
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".logD(tag = TAG)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        "onActivitySaveInstanceState : ${activity.localClassName}".logD(tag = TAG)
    }

    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".logD(tag = TAG)
        ActivityManagerKtx.popActivity(activity)
    }
}