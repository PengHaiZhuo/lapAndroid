package com.phz.common.page.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.getVmClazz
import com.phz.common.ext.showLoadingExt
import com.phz.common.net.manager.NetState
import com.phz.common.net.manager.NetStateManager
import com.phz.common.state.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author phz
 * @description Fragment基类 VM+DB
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {
    lateinit var mActivity: AppCompatActivity

    //当前Fragment绑定的泛型类ViewModel
    lateinit var mViewModel: VM
    //子类直接拿mViewDataBinding获取子View操作就行
    lateinit var mViewDataBinding: DB

    //是否加载过，为了实现Androidx下懒加载
    protected var isLoaded = false

    //当前碎片布局的ViewDataBinding的根布局
    var mRootView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

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
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取viewModel
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
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
        initObserver()
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
        mRootView = null
        isLoaded = false
    }

    /**
     * 懒加载
     */
    abstract fun lazyInit()

    abstract fun getLayoutId(): Int

    /**
     * 创建观察者
     */
    open fun initObserver() {}

    /**
     * 可以在此方法内加载数据，配合setOnclick()拓展函数设置点击事件
     */
    abstract fun initData()

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 网络变化监听 子类重写即可获取网络状态监听回调
     */
    open fun onNetworkStateChanged(netState: NetState) {}
}