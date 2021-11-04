package com.phz.common.navigation

import android.view.View
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.phz.common.R

/**
 * @author phz on 2021/8/19
 * @description copy from 2.4.0-alpha01
 * 目前有问题，报错:
 * FragmentManager: saveBackStack must be self contained and not reference fragments from non-saved FragmentTransactions.
 */
@Deprecated("暂时有问题，返回栈问题",ReplaceWith("官方的NavHostFragment"))
class NavHostFragmentHideShow: NavHostFragment() {

    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return FragmentNavigatorHideShow(requireContext(), childFragmentManager, containerId)
    }

    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
            } else R.id.nav_host_fragment_container
            // Fallback to using our own ID if this Fragment wasn't added via
            // add(containerViewId, Fragment)
        }

}