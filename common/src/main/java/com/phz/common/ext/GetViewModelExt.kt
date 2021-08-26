package com.phz.common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.phz.common.BaseApplication
import com.phz.common.state.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 * 要求Class类 泛型第一个是VM
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

/**
 * 获取当前类绑定的泛型ViewDataBinding-clazz
 * 要求Class 泛型第二个是DB
 */
fun <DB> getDbClazz(obj: Any): DB {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as DB
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> AppCompatActivity.getAppViewModel(): VM {
    (this.application as? BaseApplication).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApplication类，暂时无法使用getAppViewModel方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getAppViewModel(): VM {
    (this.requireActivity().application as? BaseApplication).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApplication类，暂时无法使用getAppViewModel该方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}

/**
 * 得到当前Activity上下文的ViewModel
 * exp{ val viewModel by viewModels<MyViewModel>() }
 */
@Deprecated("已过时的方法，现在可以直接使用Ktx函数 viewModels()获取")
inline fun <reified VM : BaseViewModel> AppCompatActivity.getViewModel(): VM {
    return ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory(application)
    ).get(VM::class.java)
}

/**
 * 得到当前Fragment上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
@Deprecated("已过时的方法，现在可以直接使用Ktx函数 viewModels()获取")
inline fun <reified VM : BaseViewModel> Fragment.getViewModel(): VM {
    return ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
    ).get(VM::class.java)
}

/**
 * 在Fragment中得到父类Activity的共享ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 * exp { val viewModel by activityViewModels<MyViewModel>() }
 */
@Deprecated("已过时的方法，现在可以直接使用Ktx函数 activityViewModels()获取")
inline fun <reified VM : BaseViewModel> Fragment.getActivityViewModel(): VM {
    return ViewModelProvider(requireActivity(),
        ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
    ).get(VM::class.java)
}