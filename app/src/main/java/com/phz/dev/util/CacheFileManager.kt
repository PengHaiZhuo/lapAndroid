package com.phz.dev.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * 缓存文件管理
 */
object CacheFileManager {

    /**
     * 获取文件下缓存文件大小，包含2部分cacheDir和externalCacheDir
     */
    fun getTotalCacheSize(context: Context): String {

        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清理缓存
     */
    fun clearAllCache(context: Context):Boolean {
        var resultDeleteCache = false
        var resultDeleteExCache = false
        resultDeleteCache = deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            context.externalCacheDir?.let { file ->
                resultDeleteExCache = deleteDir(file)
            }
        }
        return resultDeleteCache and resultDeleteExCache
    }

}

/**
 * 删除目录
 */
private fun deleteDir(dir: File): Boolean {
    if (dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir.delete()
}

/**
 * 获取文件
 * Context.getExternalFilesDir() --> sd卡/Android/data/应用的包名/files/
 * 目录，一般放一些长时间保存的数据
 * Context.getExternalCacheDir() --> sd卡/Android/data/应用包名/cache/目录，一般存放临时缓存数据
 */
fun getFolderSize(file: File?): Long {
    var size: Long = 0
    file?.run {
        try {
            val fileList = listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                size += if (fileList[i].isDirectory) {
                    getFolderSize(fileList[i])
                } else {
                    fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return size
}

/**
 * 格式化单位
 */
fun getFormatSize(size: Double): String {

    val kiloByte = size / 1024
    if (kiloByte < 1) {
        return size.toString() + "Byte"
    }

    val megaByte = kiloByte / 1024

    if (megaByte < 1) {
        val result1 = BigDecimal(kiloByte.toString())
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
    }

    val gigaByte = megaByte / 1024

    if (gigaByte < 1) {
        val result2 = BigDecimal(megaByte.toString())
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
    }

    val teraBytes = gigaByte / 1024

    if (teraBytes < 1) {
        val result3 = BigDecimal(gigaByte.toString())
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
    }

    val result4 = BigDecimal(teraBytes)
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}