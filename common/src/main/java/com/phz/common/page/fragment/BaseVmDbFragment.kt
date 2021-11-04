package com.phz.common.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.phz.common.state.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author phz
 * @description Fragment基类 VM+DB
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>() {
    //子类直接拿mViewDataBinding获取子View操作就行
    lateinit var mViewDataBinding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //利用反射 根据泛型得到 ViewDataBinding
        val superClass = javaClass.genericSuperclass
        val aClass = (superClass as ParameterizedType).actualTypeArguments[1] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.isAccessible=true
        mViewDataBinding = method.invoke(null, layoutInflater) as DB
        mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = mRootView!!.findViewTreeLifecycleOwner()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}