package com.phz.common.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.getVmClazz
import com.phz.common.ext.showLoadingExt
import com.phz.common.state.BaseViewModel

/**
 * @author phz
 * @description Fragment纯净版 VM+DB,不使用activity_base作为父布局
 */
abstract class BaseVmDbPureActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    AppCompatActivity() {
    lateinit var mViewModel: VM
    lateinit var mViewDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mViewDataBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        initView(savedInstanceState)
        //注册界面响应事件观察者
        mViewModel.showLoading.observe(this@BaseVmDbPureActivity) {
            if (it) {
                //显示加载对话框
                showLoadingExt()
            } else {
                //隐藏加载对话框
                dismissLoadingExt()
            }
        }
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