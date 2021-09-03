package com.phz.common.ext

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author phz
 * @description
 */
fun AppCompatActivity.loadImageUrl(
    url: String,
    scope: CoroutineScope,
    widthPx:Int=100,
    heightPX:Int=100,
    callback: (bitmap: Bitmap) -> Unit
) {
    scope.launch(Dispatchers.IO) {
        val bitmap = Glide.with(this@loadImageUrl).asBitmap().load(url).submit(widthPx, heightPX).get()
        withContext(Dispatchers.Main) {
            callback(bitmap)
        }
    }
}

fun Fragment.loadImageUrl(
    url: String,
    scope: CoroutineScope,
    widthPx:Int=100,
    heightPX:Int=100,
    callback: (bitmap: Bitmap) -> Unit
) {
    scope.launch(Dispatchers.IO) {
        val bitmap = Glide.with(this@loadImageUrl).asBitmap().load(url).submit(widthPx, heightPX).get()
        withContext(Dispatchers.Main) {
            callback(bitmap)
        }
    }
}

fun AppCompatActivity.loadCircleImageUrl(
    url: String,
    scope: CoroutineScope,
    callback: (bitmap: Bitmap) -> Unit,
    widthPx:Int=100,
    heightPX:Int=100
) {
    scope.launch(Dispatchers.IO) {
        val bitmap = Glide.with(this@loadCircleImageUrl).asBitmap().load(url).transform(CircleCrop()).submit(widthPx, heightPX).get()
        withContext(Dispatchers.Main) {
            callback(bitmap)
        }
    }
}

fun Fragment.loadCircleImageUrl(
    url: String,
    scope: CoroutineScope,
    callback: (bitmap: Bitmap) -> Unit,
    widthPx:Int=100,
    heightPX:Int=100
) {
    scope.launch(Dispatchers.IO) {
        val bitmap = Glide.with(this@loadCircleImageUrl).asBitmap().load(url).transform(CircleCrop()).submit(widthPx, heightPX).get()
        withContext(Dispatchers.Main) {
            callback(bitmap)
        }
    }
}