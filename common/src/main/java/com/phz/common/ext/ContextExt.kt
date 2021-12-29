package com.phz.common.ext

import android.content.ClipData
import android.content.Context
import android.content.pm.PackageManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.phz.common.util.NetWorkUtils

/**
 * @author phz
 * @description context上下文扩展方法
 */
//是否连接网络
val Context.isNetWorkAvailable get()= NetWorkUtils.pingBaidu()
//连接是否花费手机流量
val Context.isNetWorkBilling get() = this.isNetWorkBeep()
//是否是蜂窝网络
fun Context.isNetWorkBeep():Boolean{
        val capabilities = connectivityManager?.getNetworkCapabilities(connectivityManager?.activeNetwork)
        return capabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                else ->false
            }
        }?:false
}
//====++++----*******----++++========++++----*******----++++========++++----*******----++++====
//获取appName
fun Context.getAppName():String{
    try {
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return appInfo.loadLabel(packageManager).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}
//获取versionName
fun Context.getVersionName(): String {
    var versionName = ""
    try {
        versionName = packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return versionName
}
//获取versionCode
fun Context.getVersionCode(): Int {
    var versionCode = 0
    try {
        val pi = packageManager.getPackageInfo(packageName, 0)
        versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pi.longVersionCode as Int
        } else {
            pi.versionCode
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return versionCode
}
//获取packageName
fun Context.getPackageName(): String {
    try {
        val pi = packageManager.getPackageInfo(packageName, 0)
        return pi.packageName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}
//====++++----*******----++++========++++----*******----++++========++++----*******----++++====
//设置剪贴板内容
fun Context.setClipboard(text: String) {
    clipboardManager?.let {
        it.setPrimaryClip(ClipData.newPlainText(null, text))
        Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show()
    }
}
