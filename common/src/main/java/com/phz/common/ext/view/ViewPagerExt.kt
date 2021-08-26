package com.phz.common.ext.view

import androidx.collection.LongSparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

/**
 * @author phz on 2021/6/9
 * @description ViewPager2 扩展函数
 */
/** 获取FragmentManager中指定Fragment引用**/
inline fun <reified T : Fragment> ViewPager2.getFragment(
    fragmentManager: FragmentManager,
    currentPosition: Int
): T? =
    when (adapter) {
        is FragmentPagerAdapter -> {
            //tag来自于 FragmentPagerAdapter#makeFragmentName内部逻辑
            fragmentManager.findFragmentByTag("android:switcher:$id:$currentPosition") as? T
        }
        //FragmentStatePagerAdapter内没有makeFragmentName方法
        //通过分析使用instantiateItem方法获取某个fragment不可取，如果传入的fragment为null，页面会初始化一次，经历一系列设置，生命周期可能会重复调用
        //所以使用FragmentManager通过反射的方式获取fragment比较好
//        is FragmentStatePagerAdapter -> {
//            //第一次添加界面的时候，由于FragmentManager中没有这个Fragment，因此需要通过自定义的FragmentPagerAdapter获取，然后调用add方法，所走的生命周期为onAttach() -> onResume()。
//            //移除界面时，使用的是detach方法，接触过Fragment的人都知道，这时候仅仅是Fragment的界面被从View树上移除了而已，它的实例仍然被保存在FragmentManager当中，所走的生命周期为onPause() -> onDestroyView()。
//            //重新添加界面时，由于此时去FragmentManager中能找到那个Fragment，所以调用的是attach方法，所走的生命周期为onCreateView() -> onResume()，并且不需要再从自定义的FragmentPagerAdapter中获取Fragment。
//            //通过调用instantiateItem获取fragment不可取
//            ((adapter as FragmentStatePagerAdapter).instantiateItem(this, currentPosition)) as T?
//        }
        else -> null
    }


/**
 * 通过index获取Fragment
 * 适用于一般的FragmentPagerAdapter和FragmentStatePagerAdapter
 */
fun ViewPager.getFragmentByPage(index: Int): Fragment? {
    return when (adapter) {
        is FragmentPagerAdapter -> {
            val clazz = (adapter as FragmentPagerAdapter)::class.java
            //通过Class的getDeclaredField方法获取Filed
            val mFragments = clazz.getDeclaredField("mFragments")
            //获取到的Filed记得设置isAccessible
            mFragments.isAccessible = true
            //通过Field的get方法获取对象
            val fragments = mFragments.get(adapter) as LongSparseArray<Fragment>
            fragments[index.toLong()]
        }
        is FragmentStatePagerAdapter -> {
            val clazz = (adapter as FragmentStatePagerAdapter)::class.java
            //通过Class的getDeclaredField方法获取Filed
            val mFragments = clazz.getDeclaredField("mFragments")
            //获取到的Filed记得设置isAccessible
            mFragments.isAccessible = true
            //通过Field的get方法获取对象
            val fragments = mFragments.get(adapter) as LongSparseArray<Fragment>
            fragments[index.toLong()]
        }
        else -> null
    }
}

/*fun ViewPager.getFragmentByPage(currentPosition: Int): Fragment? {
    when (adapter) {
        is FragmentPagerAdapter -> {
            val fragmentPagerAdapter = adapter as FragmentPagerAdapter
            //调用getItemId方法获取id
            val id = fragmentPagerAdapter.getItemId(currentPosition)
            val fpaClazz = (adapter as FragmentPagerAdapter)::class.java
            //调用Class的getDeclaredField方法获取包含某个name的Filed对象 {mFragmentManager in FragmentPagerAdapter.java}
            val fragmentManagerField = fpaClazz.getDeclaredField("mFragmentManager")
            //可能抛出SecurityException，这里是设置反射对象在使用时应禁止 Java 语言访问检查
            fragmentManagerField.isAccessible = true
            //通过Field的get方法获取对象,传入一个object对象，这里是adapter，具体看上文fpaClazz赋值
            val fragmentManager = fragmentManagerField.get(adapter) as FragmentManager
            //通过Class的getDeclaredMethod方法获取Method对象 {反射获取方法 makeFragmentName in FragmentPagerAdapter}
            val method =fpaClazz.getDeclaredMethod("makeFragmentName", Int::class.java, Long::class.java)
            //将此方法的isAccessible标志设置为true，禁止java语言访问检查
            method.isAccessible = true
            //通过反射调用方法makeFragmentName并获取结果
            //如果底层方法是静态的，则第一个参数obj可以传null,否则需要传一个此class的实例化对象，fpaClazz.newInstance()
            val name = method.invoke(null, this.id, id) as String
            return fragmentManager.findFragmentByTag(name)
        }
        else -> {
            return null
        }
    }
}*/


