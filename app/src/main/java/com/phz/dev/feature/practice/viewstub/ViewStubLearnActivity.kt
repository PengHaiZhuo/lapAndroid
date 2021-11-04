package com.phz.dev.feature.practice.viewstub

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.showLoadingExt
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityViewstubLearnBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/9/2
 * @description 关于ViewStub
 * /*要被加载的布局通过 android:layout 属性来设置. 然后在程序中调用 inflate() 方法来加载.*/
 * /*还可以设定 Visibility 为 VISIBLE 或 INVISIBLE, 也会触发 inflate().*/
 * /*但只有直接使用 inflate() 方法能返回布局文件的根 View. 但是这里只会在首次使用 setVisibility() 会加载要渲染的布局文件.*/
 * /*再次使用只是单纯的设置可见性.对 inflate() 操作也只能进行一次, 因为 inflate() 的时候是其指向的布局文件替换掉当前 <ViewStub> 标签.*/
 * /*之后, 原来的布局文件中就没有 <ViewStub> 标签了.*/
 * /*因此多次 inflate() 操作, 会报错: ViewStub must have a non-null ViewGroup viewParent*/
 */
class ViewStubLearnActivity :
    BaseVmDbActivity<ViewStubLearnViewModel, ActivityViewstubLearnBinding>() {
    override fun initData() {
        lifecycleScope.launchWhenResumed {
            //模拟请求
            showLoadingExt()
            delay(1000)
            dismissLoadingExt()
            //模拟显示空布局
            mViewDataBinding.vb.viewStub?.visibility = View.VISIBLE
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "ViewStub"

        mViewDataBinding.vb.setOnInflateListener { _, _ ->
            val llEmpty = findViewById<LinearLayout>(R.id.ll_empty)
            llEmpty.setOnClickListener {
                //隐藏空布局
                mViewDataBinding.vb.viewStub?.visibility = View.GONE
                llEmpty.isClickable=false //我不知道为啥还需要这么设置，但是不加这句，设置不可见后依然可以响应点击（这不符合常理）
                //模拟请求成功并设置背景
                lifecycleScope.launch {
                    showLoadingExt()
                    delay(1000)
                    dismissLoadingExt()
                    mViewDataBinding.iv.setImageResource(R.drawable.bg_login)
                }
            }
        }
    }
}