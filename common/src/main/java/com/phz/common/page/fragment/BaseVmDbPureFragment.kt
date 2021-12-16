package com.phz.common.page.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.phz.common.ext.getVmClazz
import com.phz.common.state.BaseViewModel

/**
 * @author phz
 * @description Fragment基类纯净版，做了懒加载处理
 */
abstract class BaseVmDbPureFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    Fragment() {
    lateinit var mActivity: AppCompatActivity
    //当前Fragment绑定的泛型类ViewModel
    lateinit var mViewModel: VM
    //该类绑定的ViewDataBinding
    lateinit var mViewDataBinding: DB
    //是否加载过，为了实现Androidx下懒加载
    protected var isLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewDataBinding.lifecycleOwner = mViewDataBinding.root.findViewTreeLifecycleOwner()
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取viewModel
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        initView(savedInstanceState)
        initData()
    }


    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    /**
     * 懒加载
     */
    abstract fun lazyInit()
    abstract fun getLayoutId(): Int
    /**
     * 可以在此方法内加载数据，创建数据源观察者，配合setOnclick()拓展函数设置点击事件
     */
    abstract fun initData()
    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

}