package com.phz.common.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phz.common.ext.getVmClazz

/**
 * @author phz
 * @description Activity普通版 VM+DB
 */
abstract class BasePureActivity<VM : ViewModel, DB : ViewDataBinding> :
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
        initObserver()
    }

    /*设置布局id*/
    abstract fun layoutId(): Int

    /*初始化view*/
    abstract fun initView(savedInstanceState: Bundle?)

    /*加载数据，设置适配器，设置点击事件*/
    abstract fun initData()

    /*初始化数据观察员*/
    abstract fun initObserver()
}