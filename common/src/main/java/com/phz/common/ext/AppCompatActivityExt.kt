package com.phz.common.ext

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author phz
 * @description 活动页扩展方法
 */
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