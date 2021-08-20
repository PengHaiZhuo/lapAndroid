package com.phz.common.navigation

import android.content.Context
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import kotlin.properties.Delegates

/**
 * @author phz on 2021/8/19
 * @description copy from 2.4.0-alpha01
 */
@Navigator.Name("fragment")
@Deprecated("暂时有问题，返回栈问题",ReplaceWith("FragmentNavigator"))
class FragmentNavigatorHideShow : FragmentNavigator {
    private lateinit var mSavedIds: MutableSet<String>
    private lateinit var mContext: Context
    private lateinit var mFragmentManager: FragmentManager
    private var mContainerId by Delegates.notNull<Int>()

    constructor(
        context: Context,
        fragmentManager: FragmentManager,
        containerId: Int
    ) : super(context, fragmentManager, containerId) {
        try {
            //通过反射获取savedIds
            val clazz = this.javaClass.superclass
            //通过Class的getDeclaredField方法获取Filed
            val filed = clazz.getDeclaredField("savedIds")
            //获取到的Filed记得设置isAccessible
            filed.isAccessible = true
            mSavedIds = filed.get(this) as MutableSet<String>
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        mContext = context
        mFragmentManager = fragmentManager
        mContainerId = containerId
    }

//    override fun navigate(
//        entries: List<NavBackStackEntry>,
//        navOptions: NavOptions?,
//        navigatorExtras: Navigator.Extras?
//    ) {
//        if (mFragmentManager.isStateSaved) {
//            Log.i(
//                TAG, "Ignoring navigate() call: FragmentManager has already saved its state"
//            )
//            return
//        }
//        for (entry in entries) {
//            navigate(entry, navOptions, navigatorExtras)
//        }
//    }

//    private fun navigate(
//        entry: NavBackStackEntry,
//        navOptions: NavOptions?,
//        navigatorExtras: Navigator.Extras?
//    ) {
//        val backStack = state.backStack.value
//        val initialNavigation = backStack.isEmpty()
//        val restoreState = (
//                navOptions != null && !initialNavigation &&
//                        navOptions.shouldRestoreState() &&
//                        mSavedIds.remove(entry.id)
//                )
//        if (restoreState) {
//            // Restore back stack does all the work to restore the entry
//            mFragmentManager.restoreBackStack(entry.id)
//            state.add(entry)
//            return
//        }
//        val destination = entry.destination as Destination
//        val args = entry.arguments
//        var className = destination.className
//        if (className[0] == '.') {
//            className = mContext.packageName + className
//        }
////        val frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, className)
////        frag.arguments = args
//        val ft = mFragmentManager.beginTransaction()
//        var enterAnim = navOptions?.enterAnim ?: -1
//        var exitAnim = navOptions?.exitAnim ?: -1
//        var popEnterAnim = navOptions?.popEnterAnim ?: -1
//        var popExitAnim = navOptions?.popExitAnim ?: -1
//        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
//            enterAnim = if (enterAnim != -1) enterAnim else 0
//            exitAnim = if (exitAnim != -1) exitAnim else 0
//            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
//            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
//            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
//        }
//        val fragment = mFragmentManager.primaryNavigationFragment
//        if (fragment != null) {
//            ft.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
//            ft.hide(fragment)
//        }
//        var frag: Fragment? = null
//        val tag = destination.id.toString()
//        frag = mFragmentManager.findFragmentByTag(tag)
//        if (frag != null) {
//            ft.setMaxLifecycle(frag, Lifecycle.State.RESUMED)
//            ft.show(frag)
//        } else {
//            frag = instantiateFragment(mContext, mFragmentManager, className, args)
//            frag.arguments = args
//            ft.add(mContainerId, frag, tag)
//        }
////        ft.replace(mContainerId, frag)
//        ft.setPrimaryNavigationFragment(frag)
//        @IdRes val destId = destination.id
//        // TODO Build first class singleTop behavior for fragments
//        val isSingleTopReplacement = (
//                navOptions != null && !initialNavigation &&
//                        navOptions.shouldLaunchSingleTop() &&
//                        backStack.last().destination.id == destId
//                )
//        val isAdded = when {
//            initialNavigation -> {
//                true
//            }
//            isSingleTopReplacement -> {
//                // Single Top means we only want one instance on the back stack
//                if (backStack.size > 1) {
//                    // If the Fragment to be replaced is on the FragmentManager's
//                    // back stack, a simple replace() isn't enough so we
//                    // remove it from the back stack and put our replacement
//                    // on the back stack in its place
//                    mFragmentManager.popBackStack(
//                        entry.id,
//                        FragmentManager.POP_BACK_STACK_INCLUSIVE
//                    )
//                    ft.addToBackStack(entry.id)
//                }
//                false
//            }
//            else -> {
//                ft.addToBackStack(entry.id)
//                true
//            }
//        }
//        if (navigatorExtras is Extras) {
//            for ((key, value) in navigatorExtras.sharedElements) {
//                ft.addSharedElement(key, value)
//            }
//        }
//        ft.setReorderingAllowed(true)
//        ft.commit()
//        // The commit succeeded, update our view of the world
//        if (isAdded) {
//            state.add(entry)
//        }
//    }


    companion object {
        private const val TAG = "HSFragmentNavigator"
    }
}