package com.phz.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.io.IOException

/**
 * @author phz
 * @description 网络链接工具
 */
object NetWorkUtils {
    /**
     * 判断网络是否可用
     * @return
     */
    fun isNetConnected(context: Context?): Boolean {
        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //NetworkInfo类在api29过时了，可以使用NetworkCapabilities
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                //不包括 蓝牙传输、VPN传输和LOWPAN传输，tips:其实可以使用ping命令判断网络是否可用
                if (capabilities != null) {
                    when {
                        //蜂窝
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        //wifi
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        //以太网
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        return true
                    }
                } catch (e: Exception) {
                    Log.i("update_status", "" + e.message)
                }
            }
        }
        return false
    }

    /**
     * 判断网络是否可用
     * 使用ping命令，由结果判断网络状态
     */
    fun pingBaidu(): Boolean {
        //android10开始，移除了应用主目录的执行权限，不可信应用无法再针对应用主目录中的文件调用 exec()
        //不过android10还可以通过设置继续用，android11就不行了
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("ping -c 3 www.baidu.com")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}