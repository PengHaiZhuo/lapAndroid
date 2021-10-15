package com.phz.dev.feature.main

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider.getUriForFile
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMainBinding
import java.io.File
import java.net.URL

/**
 * @author phz
 * @description
 */
class MainActivity : BaseVmDbPureActivity<BaseViewModel, ActivityMainBinding>() {

    companion object {
        const val pdfUrl =
            "https://download.brother.com/welcome/docp000648/cv_pt3600_schn_sig_lad962001.pdf"
        const val REQUEST_STORAGE = 0x115
        const val REQUEST_LOCATION = 0x116
        const val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE

        //in Android 10 WRITE_EXTERNAL_STORAGE默认无法获取，除非在配置文件中添加 android:requestLegacyExternalStorage="true"
        //in Android 11, 只有READ_EXTERNAL_STORAGE请求会弹出对话框，仅仅请求访问照片和媒体，WRITE_EXTERNAL_STORAGE无法获取，requestLegacyExternalStorage无效。
        //从Android 10开始，需要做分区适配了，使用MediaStore
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }
        val context=this@MainActivity.baseContext
        val file=File(context.filesDir,"xImages")
        val newFile=File(file,"dddd.png")
        val contentUri = getUriForFile(context,context.packageName,newFile)
        contentUri.toString().logE()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            lifecycleScope.launch() {
//                withContext(Dispatchers.IO) {
//                    savePDFUsingMediaStore(
//                        this@MainActivity,
//                        pdfUrl,
//                        "xxxxxx.pdf"
//                    )
//                }
//            }
//        }

    }

    //todo MediaStore 方式读写实践
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun savePDFUsingMediaStore(context: Context, url: String, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        uri.toString().logE()
        if (uri != null) {
            URL(url).openStream().use { input ->
                resolver.openOutputStream(uri).use { output ->
                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                }
            }
        }
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