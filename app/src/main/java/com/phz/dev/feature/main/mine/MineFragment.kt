package com.phz.dev.feature.main.mine

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlGifRes
import com.phz.common.ext.*
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.util.ActivityManagerKtx
import com.phz.dev.R
import com.phz.dev.databinding.FragmentMineBinding
import com.phz.dev.ext.request
import com.phz.dev.feature.practice.PracticeMainActivity
import com.phz.dev.net.apiService
import com.phz.dev.state.AppViewModel
import com.phz.dev.util.CacheFileManager
import com.phz.dev.util.PersistenceUtil
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/8/17
 * @description 我的页面
 */
class MineFragment : BaseVmDbPureFragment<MineViewModel, FragmentMineBinding>() {
    val appViewModel: AppViewModel by lazy { getAppViewModel() }
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData() {
        mViewDataBinding.tvCacheSize.text= CacheFileManager.getTotalCacheSize(requireContext())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.vm = mViewModel
        mViewDataBinding.clickProxy = ProxyClick()
        mViewDataBinding.swipeRl.apply {
//            setSize(SwipeRefreshLayout.LARGE) //设置尺寸
//            setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新时颜色变换
            setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )//设置刷新时颜色变换
            setBackgroundColor(Color.WHITE)//设置背景色
            setDistanceToTriggerSync(96)//设置下拉距离
            setOnRefreshListener {
                //todo 从网络获取积分 返回结果后手动停止动画
                postDelayed({
                    circleImageUrlGifRes(mViewDataBinding.ivAvatar, mViewModel.gifUrl)
                    mViewDataBinding.swipeRl.isRefreshing = false
                }, 2000)

            }
        }
    }


    inner class ProxyClick {
        //积分
        fun integral() {
            nav().navigate(R.id.action_mine_to_integral)
        }

        //文章
        fun article() {}

        //收藏
        fun star() {}

        //待办
        fun todo() {}

        //我的实践
        fun practice() {
            context!!.startKtxActivity<PracticeMainActivity>()
        }

        //清理缓存
        fun clear() {
            val result=CacheFileManager.clearAllCache(requireContext())
            if (result){
                Toast.makeText(requireContext(), "清除缓存成功", Toast.LENGTH_SHORT).show()
            }
        }

        //检查更新
        fun update() {}

        //退出
        fun exit() {
            showDialogMessage(
                "确认要退出吗？", "提示",
                positiveAction = {
                    mViewModel.viewModelScope.launch {
                        mViewModel.request({ apiService.logout() }, {
                            appViewModel.userBean.value = null
                            PersistenceUtil.clearCache()
                            ActivityManagerKtx.removeAllActivity()
                        }, {
                            it.message?.logE()
                        })

                    }
                }, negativeButtonText = "取消"
            )
        }
    }
}