package com.phz.common.page.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phz.common.ext.getVmClazz

/**
 * @author phz
 * @description Fragment纯净版 VM+DB,不使用activity_base作为父布局
 */
abstract class BaseVmDbPureActivity<VM : ViewModel, DB : ViewDataBinding> :
    AppCompatActivity() {
    lateinit var mViewModel: VM
    lateinit var mViewDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mViewDataBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        initView(savedInstanceState)
        initData()
    }


    abstract fun layoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 可以在此方法内加载数据，创建数据源观察者，配合setOnclick()拓展函数设置点击事件
     */
    abstract fun initData()
}