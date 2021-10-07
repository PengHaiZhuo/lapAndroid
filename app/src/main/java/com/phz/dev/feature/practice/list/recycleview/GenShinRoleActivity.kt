package com.phz.dev.feature.practice.list.recycleview

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.logE
import com.phz.common.ext.showLoadingExt
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityGenshinRoleBinding
import com.phz.dev.feature.practice.list.recycleview.adapter.GenShinRoleAdapter
import com.phz.dev.feature.practice.list.recycleview.state.GenShinRoleListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleActivity :
    BaseVmDbActivity<GenShinRoleListViewModel, ActivityGenshinRoleBinding>() {

    private lateinit var mAdapter:GenShinRoleAdapter

    override fun initData() {
        mAdapter=GenShinRoleAdapter(this::toggleLike)
        mViewDataBinding.rvGenshinRole.apply {
            vertical()
            adapter=mAdapter
        }
        lifecycleScope.launch {
            //模拟网络请求
            mViewModel.showLoading.value=true
            delay(1000)
            mViewModel.showLoading.value=false
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                mViewModel.roles.collect {
                    //StateFlow的collect有点类似LiveData的observe，当数据源发生变化时，会跑到这里来
                    mAdapter.submitList(it)
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "局部刷新"
    }

    private fun toggleLike(id:Int){
        mViewModel.toggleLikeStatus(id)
    }
}