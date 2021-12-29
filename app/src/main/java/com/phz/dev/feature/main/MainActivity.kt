package com.phz.dev.feature.main

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.logE
import com.phz.common.ext.showSnackShort
import com.phz.common.net.manager.NetStateManager
import com.phz.common.net.manager.NetStateReceiver
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author phz
 * @description
 */
class MainActivity : BaseVmDbPureActivity<NoViewModel, ActivityMainBinding>() {
    private lateinit var mNetStateReceiver: NetStateReceiver

    companion object {
        const val REQUEST_STORAGE = 0x115
        const val REQUEST_LOCATION = 0x116

        const val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        const val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        //确切定位权限
        const val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION

        //大致定位权限
        const val crossLocation = Manifest.permission.ACCESS_COARSE_LOCATION

        //相机 实景导航要用
        const val camera = Manifest.permission.CAMERA
    }

    override fun initData() {
        //6.0运行时权限-读写权限获取
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            var requestPerms = arrayListOf<String>()
            if (checkPermission(
                    readPermission, Process.myPid(),
                    Process.myUid()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPerms.add(readPermission)
            }
            if (checkPermission(
                    writePermission, Process.myPid(),
                    Process.myUid()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPerms.add(writePermission)
            }
            if (requestPerms.isNotEmpty()) {
                requestPermissions(requestPerms.toTypedArray(), REQUEST_STORAGE)
            }
        } else {
            //11开始请求写权限没用了
            if (checkPermission(
                    readPermission, Process.myPid(),
                    Process.myUid()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(readPermission),
                    REQUEST_STORAGE
                )
            }
        }
        //注册网络变化监听广播
        mNetStateReceiver = NetStateReceiver()
        registerReceiver(
            mNetStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )//虽然被标记为过时，但是动态注册还是能用的

        var isInit=false//用来解决第一次监听回调，没有发生变化也回调了，毕竟用了flow
        lifecycleScope.launch {
            //添加网络状态变化观察者
            NetStateManager.instance.isNetAvailable.collect {
                if (isInit){
                    showSnackShort(
                        if (it) {
                            "有网"
                        } else {
                            "断网"
                        }
                    )
                }else{
                    isInit=true
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //解除注册网络变化监听广播
        unregisterReceiver(mNetStateReceiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_STORAGE) {
            for ((index, value) in grantResults.withIndex()) {
                when (value) {
                    //获取权限结果打印
                    PackageManager.PERMISSION_GRANTED -> {
                        "${permissions[index]} allow".logE()
                    }
                    PackageManager.PERMISSION_DENIED -> {
                        "${permissions[index]} deny".logE()
                    }
                }

            }
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun initView(savedInstanceState: Bundle?) {
        //布局文件中使用的FragmentContainerView而不是fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        mViewDataBinding.bottomNav.setupWithNavController(navHostFragment.navController)
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            autoDarkModeEnable(true)
        }
    }

    override fun layoutId(): Int = R.layout.activity_main
}