package com.phz.common.net.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.phz.common.ext.isNetWorkAvailable

/**
 * @author phz
 * @description 网络状态监听广播
 */
class NetStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (!context.isNetWorkAvailable) {//没网
                if (NetStateManager.instance.isNetAvailable.value) {
                    NetStateManager.instance.isNetAvailable.value = false
                }
            } else {//有网
                if (!NetStateManager.instance.isNetAvailable.value) {
                    NetStateManager.instance.isNetAvailable.value = true
                }
            }
            return
        }
    }
}