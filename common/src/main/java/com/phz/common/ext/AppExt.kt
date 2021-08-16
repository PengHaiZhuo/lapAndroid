package com.phz.common.ext

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.phz.common.appContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 获取versionName
 */
fun getVersionName(context: Context): String {
    try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        return pi.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取versionCode
 */
fun getVersionCode(context: Context): Long {
    try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            pi.versionCode.toLong()
        }else{
            pi.longVersionCode
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return 0
}

/**
 * 获取packageName
 */
fun getPackageName(context: Context): String {
    try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        return pi.packageName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取appName
 */
fun getAppName(context:Context):String{
    try {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return appInfo.loadLabel(context.packageManager).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}


/**
 * 获取当前进程的名称，默认进程名称是包名
 */
val currentProcessName: String?
    get() {
        val pid = android.os.Process.myPid()
        val mActivityManager = appContext.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }

fun AppCompatActivity.showSnackShort(text: CharSequence) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.showSnackShort(@StringRes text: Int) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

/**
 * 使用glide获取bitmap
 */
fun AppCompatActivity.loadImageUrl(
    url: String,
    scope: CoroutineScope,
    callback: (bitmap: Bitmap) -> Unit
) {
    scope.launch(Dispatchers.IO) {
        val bitmap = Glide.with(this@loadImageUrl).asBitmap().load(url).submit(100, 100).get()
        withContext(Dispatchers.Main) {
            callback(bitmap)
        }
    }
}