package com.phz.common.util

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

/**
 * @author phz
 * @description bitmap相关
 */
object BitmapUtils {
    /**
     * bitmap转字节数组
     */
    fun bitmapToByteArray(
        bitmap: Bitmap,
        compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        compressQuality: Int = 100
    ): ByteArray {
        val outputSteam = ByteArrayOutputStream()
        bitmap.compress(compressFormat, compressQuality, outputSteam)
        val result = outputSteam.toByteArray()
        kotlin.runCatching {
            outputSteam.close()
        }.onFailure {
            it.printStackTrace()
        }
        return result
    }


}