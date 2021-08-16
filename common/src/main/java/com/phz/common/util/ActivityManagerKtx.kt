package com.phz.common.util

import android.app.Activity
import java.util.*

/**
 * @author phz
 * @description 活动堆栈式管理
 */
object ActivityManagerKtx {
    private val mActivityStack = Stack<Activity>()

    /**
     * 当前栈顶activity
     */
    val currentActivity: Activity?
        get() =
            if (mActivityStack.isEmpty()) null
            else mActivityStack.lastElement()

    /**
     * activity入栈
     */
    fun pushActivity(activity: Activity) {
        mActivityStack.add(activity);
    }

    /**
     * activity出栈
     */
    fun popActivity(activity: Activity) {
        mActivityStack.remove(activity)
    }


    /**
     * 关闭当前activity
     * @description 调用finish方法后不会马上调用onDestroy，调用finish后activity进入后台状态，由系统服务统一管理何时被杀。
     * 举个常见的例子，B返回A，A的onResume调用后，B的onDestroy才调用。
     */
    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    /**
     * 关闭指定的activity
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        mActivityStack.remove(activity)//这一行可要可不要
    }

    /**
     * 关闭指定类名的activity
     */
    fun finishActivity(clazz: Class<*>) {
        for (activity in mActivityStack)
            if (activity.javaClass == clazz)
                activity.finish()
    }

    /**
     * 全部出栈
     */
    fun removeAllActivity() {
        mActivityStack.forEach {
            if (!it.isFinishing) {
                it.finish()
            }
        }
        mActivityStack.clear()
    }
}