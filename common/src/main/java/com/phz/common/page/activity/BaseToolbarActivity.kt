package com.phz.common.page.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.R
import com.phz.common.ext.getVmClazz
import com.phz.common.ext.view.visibleOrGone
import java.lang.reflect.ParameterizedType

/**
 * @author phz
 * @description 带工具栏的Activity基类 VM+DB
 */
abstract class BaseToolbarActivity<VM : ViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    //当前Activity绑定的 ViewModel
    lateinit var mViewModel: VM
    //当前Activity的ViewDataBinding的root
    var mDataBindingView: View? = null
    //子类直接拿mViewDataBinding获取子View操作就行,基类中做了填充的操作
    lateinit var mViewDataBinding: DB

    //toolbar activity_base布局里的
    lateinit var mToolbar: MaterialToolbar
    lateinit var centerTextView: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        //获取viewModel
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        //利用反射 根据泛型得到 ViewDataBinding
        val superClass = javaClass.genericSuperclass
        val aClass = (superClass as ParameterizedType).actualTypeArguments[1] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.isAccessible=true
        mViewDataBinding = method.invoke(null, layoutInflater) as DB
        mDataBindingView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this

        //mToolbar
        mToolbar = findViewById(R.id.materialToolbar)
        //mToolbar里中间标题
        centerTextView = findViewById(R.id.centerTextView)
        mToolbar.run {
            //是否隐藏标题栏
            visibleOrGone(showToolBar())
        }
        //初始化沉浸式
        if (showToolBar()) {
            setSupportActionBar(mToolbar)
            supportActionBar?.title = ""
            immersionBar {
                titleBar(mToolbar)
                statusBarColor(R.color.colorPrimary)
                autoStatusBarDarkModeEnable(true)
            }
        }
        //添加layoutId的布局
        findViewById<FrameLayout>(R.id.baseContentView).addView(
            if (mDataBindingView == null) LayoutInflater.from(
                this
            ).inflate(R.layout.layout_empty, null) else mDataBindingView
        )
        initView(savedInstanceState)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewDataBinding.unbind()
    }

    /**
     * 是否隐藏标题栏
     * 默认显示
     * @use 子类重写设置mToolbar显示不显示
     */
    open fun showToolBar(): Boolean {
        return true
    }

    /*加载数据，设置适配器，设置点击事件,初始化数据观察员*/
    abstract fun initData()

    /*初始化view*/
    abstract fun initView(savedInstanceState: Bundle?)
}