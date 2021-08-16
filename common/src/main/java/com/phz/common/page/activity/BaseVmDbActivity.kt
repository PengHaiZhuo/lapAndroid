package com.phz.common.page.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.phz.common.state.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author phz
 * @description Activity基类 VM+DB
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    //子类直接拿mViewDataBinding获取子View操作就行
    lateinit var mViewDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        //利用反射 根据泛型得到 ViewDataBinding
        val superClass = javaClass.genericSuperclass
        val aClass = (superClass as ParameterizedType).actualTypeArguments[1] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.isAccessible=true
        mViewDataBinding = method.invoke(null, layoutInflater) as DB
        mDataBindingView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        super.onCreate(savedInstanceState)
    }
}