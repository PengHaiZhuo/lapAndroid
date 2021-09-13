package com.phz.dev.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.phz.dev.feature.practice.animation.dynamic.transformer.SimpleSliderTransformer
import kotlin.math.abs

/**
 * 处理viewpager的假拖动
 */
class ViewPagerContainer : FrameLayout {
    /**
     * 唯一child即viewpager2
     */
    private lateinit var mViewPager2: ViewPager2

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initialize()
    }

    private fun initialize() {
        //默认情况下，在绘制之前将子项裁剪到其边界,这里设置为false意味着允许绘制出其边界。
        //补充一个知识clipToPadding设置成false意味着允许绘制到padding中。
        //这2个属性经常配合动画使用，通过引入动画，实现一些效果，比如心形放大
        clipChildren = false
    }

    override fun onFinishInflate() {
        try {
            mViewPager2 = getChildAt(0) as ViewPager2
            mViewPager2.clipChildren = false
        } catch (e: Exception) {
            throw IllegalStateException("The root child of PagerContainer must be a ViewPager")
        }
        super.onFinishInflate()
    }

    fun getViewPager2():ViewPager2 {
        return mViewPager2
    }

    /**
     * Create a SimpleSliderTransformer which is PageTransformer
     *
     * @param unSelectedItemRotation set unselected item rotation in degree
     * @param unSelectedItemAlpha set unselected item opacity between 1.0f to 0.0f
     * @param minScale set min scale value for unselected item and it should between 1.0f to 0.0f
     *
     * @see SimpleSliderTransformer
     */
    fun setSimpleSlider(
        unSelectedItemRotation: Float = 120f,
        unSelectedItemAlpha: Float = 0.5f,
        minScale: Float = 0.8f
    ) {
        val transformer = SimpleSliderTransformer(
            unSelectedItemRotation = unSelectedItemRotation,
            unSelectedItemAlpha = unSelectedItemAlpha,
            minScale = minScale
        )
        mViewPager2.setPageTransformer(transformer)
    }

    private var lastXvalue = 0f
    private var lastYvalue = 0f

    private var fakeXvalue = 0f
    private var fakeYvalue = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastXvalue = event.x
                lastYvalue = event.y

                fakeXvalue = event.x
                fakeYvalue = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val value = getFakeDragDelta(event)
                if (abs(value) > 20f) {
                    if (!mViewPager2.isFakeDragging) {
                        mViewPager2.beginFakeDrag()
                    }
                    val dragValue = getLastDragDelta(event)
                    mViewPager2.fakeDragBy(dragValue)
                    fakeXvalue = event.x
                    fakeYvalue = event.y
                } else {
                    if (mViewPager2.isFakeDragging) {
                        mViewPager2.fakeDragBy(value)
                        fakeXvalue = event.x
                        fakeYvalue = event.y
                    }
                }
                lastXvalue = event.x
                lastYvalue = event.y
            }
            MotionEvent.ACTION_UP -> {
                if (mViewPager2.isFakeDragging) {
                    mViewPager2.endFakeDrag()
                }
            }
        }

        return true
    }

    private fun getFakeDragDelta(event: MotionEvent): Float {
        val x = event.x - fakeXvalue
        val y = event.y - fakeYvalue
        return if (mViewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            x
        } else {
            y
        }
    }

    private fun getLastDragDelta(event: MotionEvent): Float {
        val x = event.x - lastXvalue
        val y = event.y - lastYvalue
        return if (mViewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            x
        } else {
            y
        }
    }
}