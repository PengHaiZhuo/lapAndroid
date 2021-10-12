package com.phz.dev.feature.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMainBinding

/**
 * @author phz
 * @description
 */
class MainActivity : BaseVmDbPureActivity<BaseViewModel, ActivityMainBinding>() {

    companion object {
        const val REQUEST_STORAGE = 0x115
        const val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        //in Android 10 WRITE_EXTERNAL_STORAGE默认无法获取，除非在配置文件中添加 android:requestLegacyExternalStorage="true"
        //in Android 11, 只有READ_EXTERNAL_STORAGE请求会弹出对话框，仅仅请求访问照片和媒体，WRITE_EXTERNAL_STORAGE无法获取。
        //从Android 10开始，需要做分区适配了，可以使用MediaStore
        const val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    override fun initData() {
        //6.0运行时权限-读写权限获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_STORAGE) {
            for ((index,value)in grantResults.withIndex()){
                when(value){
                    //获取权限结果打印
                    PackageManager.PERMISSION_GRANTED ->{
                        "${permissions[index]} allow".logE()
                    }
                    PackageManager.PERMISSION_DENIED->{
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