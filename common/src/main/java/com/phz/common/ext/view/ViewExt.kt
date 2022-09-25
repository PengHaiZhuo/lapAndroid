package com.phz.common.ext.view

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

/**
 * @author phz
 * @description View扩展函数
 */
/**
 * 设置view显示
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 设置view占位隐藏
 */
fun View?.inVisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * 设置view隐藏
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

/**
 * @param visible 如果为true 该View显示 否则隐藏
 */
fun View?.visibleOrGone(visible: Boolean) {
    if (visible) {
        this.visible()
    } else {
        this.gone()
    }
}

/**
 * @param visible 如果为true 该View显示 否则占位隐藏
 */
fun View?.visibleOrInvisible(visible: Boolean) {
    if (visible) {
        this.visible()
    } else {
        this.inVisible()
    }
}


fun View?.toggle() {
    if (this!!.visibility == View.VISIBLE){
        this.gone()
    }else{
        this.visible()
    }
}

/**
 * 设置点击事件
 * @param views 需要设置点击事件的view
 * @param onClick 点击触发的方法
 */
fun setOnClick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}

/**
 * 设置防止重复点击事件
 * @param views 需要设置点击事件的view（可多个）
 * @param interval 时间间隔 默认0.5秒
 * @param onClick 点击触发的方法
 */
fun setOnClickNoRepeat(vararg views: View?, interval: Long = 500, onClick: (View) -> Unit) {
    views.forEach {
        it?.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 给view设置DynamicAnimation
 */
fun View.spring(property: DynamicAnimation.ViewProperty, value: Float) {
    val anim = SpringAnimation(this, property)
    val springForce = SpringForce()
    springForce.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    springForce.stiffness = SpringForce.STIFFNESS_LOW
    anim.spring = springForce
    anim.animateToFinalPosition(value)
}
