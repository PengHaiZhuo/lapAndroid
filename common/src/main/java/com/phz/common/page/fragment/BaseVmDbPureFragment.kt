package com.phz.common.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.phz.common.state.BaseViewModel

/**
 * @author phz
 * @description Fragment基类 VM+DB纯净版
 */
abstract class BaseVmDbPureFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseVmPureFragment<VM>() {

    //该类绑定的ViewDataBinding
    lateinit var mViewDataBinding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewDataBinding.lifecycleOwner = mViewDataBinding.root.findViewTreeLifecycleOwner()
        return mViewDataBinding.root
    }

}