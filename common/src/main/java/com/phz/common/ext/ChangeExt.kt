package com.phz.common.ext

import android.content.res.Resources

/**
 * @author phz
 * @description 转换扩展函数
 */

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.cube(): Float = this * this * this
fun Int.cube(): Int = this * this * this

fun Float.sqr(): Float = this * this
fun Int.sqr(): Int = this * this
