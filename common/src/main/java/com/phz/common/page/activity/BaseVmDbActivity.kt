package com.phz.common.page.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.R
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.getVmClazz
import com.phz.common.ext.showLoadingExt
import com.phz.common.ext.view.visibleOrGone
import com.phz.common.net.manager.NetState
import com.phz.common.net.manager.NetStateManager
import com.phz.common.state.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author phz
 * @description Activity基类 VM+DB
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
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
        //注册界面响应事件观察者
        mViewModel.showLoading.observe(this) {
            if (it) {
                //显示加载对话框
                showLoadingExt()
            } else {
                //隐藏加载对话框
                dismissLoadingExt()
            }
        }
        //添加网络状态变化观察者
        NetStateManager.instance.mNetworkStateCallback.observe(this) {
            onNetworkStateChanged(it)
        }
        initView(savedInstanceState)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewDataBinding.unbind()
    }

    /**
     * 是否隐藏 标题栏 默认显示
     * @description 子类重写设置mToolbar显示不显示
     */
    open fun showToolBar(): Boolean {
        return true
    }

    /**
     * 网络变化监听 子类重写即可获取网络状态监听回调
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 可以在此方法内加载数据，创建数据源观察者，配合setOnclick()拓展函数设置点击事件
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)
}