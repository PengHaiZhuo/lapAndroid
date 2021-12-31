package com.phz.dev.feature.practice.uriwithfilepath

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import com.phz.common.ext.view.divider
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityUriWithFilepathBinding
import com.phz.dev.feature.practice.uriwithfilepath.adapter.UriAndFilePathAdapter
import com.phz.dev.feature.practice.uriwithfilepath.bean.PathBean

/**
 * @author phz on 2021/10/15
 * @description
 */
class UriWithFilePathActivity :
    BaseToolbarActivity<UriWithFilePathViewModel, ActivityUriWithFilepathBinding>() {

    private val mContext: Context = this
    private val mAdapter by lazy { UriAndFilePathAdapter() }
    private var pathBeanList = arrayListOf<PathBean>()

    override fun initData() {
        generatePathBeanList()
        mViewDataBinding.rvUriWithFilepath.apply {
            vertical()
            divider {
                setColor("#00ff00")
            }
            adapter = mAdapter
        }
        mAdapter.submitList(pathBeanList)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "各种路径"
    }

    private fun generatePathBeanList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//api 24
            pathBeanList.add(PathBean("Context.getDataDir()", mContext.dataDir.toString()))
        }
        pathBeanList.add(PathBean("Context.getCacheDir()", mContext.cacheDir.toString()))
        pathBeanList.add(
            PathBean(
                "Context.getExternalCacheDir()",
                mContext.externalCacheDir.toString()
            )
        )
        pathBeanList.add(PathBean("Context.getFilesDir()", mContext.filesDir.toString()))
        pathBeanList.add(PathBean("Context.getObbDir()", mContext.obbDir.toString()))
        pathBeanList.add(PathBean("Context.getCodeCacheDir()", mContext.codeCacheDir.toString()))
        pathBeanList.add(
            PathBean(
                "Context.getNoBackupFilesDir()",
                mContext.noBackupFilesDir.toString()
            )
        )

        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Music\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Podcasts\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Ringtones\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_RINGTONES).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Alarms\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_ALARMS).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Notifications\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Pictures\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Movies\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Download\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"DCIM\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Context.getExternalFilesDir(\"Documents\")",
                mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {//api 29
            pathBeanList.add(
                PathBean(
                    "Context.getExternalFilesDir(\"Screenshots\")",
                    mContext.getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS).toString()
                )
            )
            pathBeanList.add(
                PathBean(
                    "Context.getExternalFilesDir(\"Audiobooks\")",
                    mContext.getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS).toString()
                )
            )
        } else {
            /**
             * api 29及以上版本这些方法已废弃，改为
             * {@link Context#getExternalFilesDir(String)},
             * {@link MediaStore}, or {@link Intent#ACTION_OPEN_DOCUMENT}.
             */
            pathBeanList.add(
                PathBean(
                    "Environment.getExternalStoragePublicDirectory(\"Download\")",
                    Environment.getExternalStoragePublicDirectory("Download").toString()
                )
            )
            pathBeanList.add(
                PathBean(
                    "Environment.getExternalStorageDirectory()",
                    Environment.getExternalStorageDirectory().toString()
                )
            )
        }
        pathBeanList.add(
            PathBean(
                "Environment.getExternalStoragePublicDirectory(\"Download\")",
                Environment.getExternalStoragePublicDirectory("Download").toString()
            )
        )
        pathBeanList.add(
            PathBean(
                "Environment.getExternalStorageDirectory()",
                Environment.getExternalStorageDirectory().toString()
            )
        )

        pathBeanList.add(
            PathBean(
                "Environment.getRootDirectory()",
                Environment.getRootDirectory().toString()
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//api 30
            pathBeanList.add(
                PathBean(
                    "Environment.getStorageDirectory()",
                    Environment.getStorageDirectory().toString()
                )
            )
        }
        pathBeanList.add(
            PathBean(
                "Environment.getDataDirectory()",
                Environment.getDataDirectory().toString()
            )
        )

        //文件创建模式：默认模式
        pathBeanList.add(
            PathBean(
                "Context.getCacheDir(“phz”，0x0000)",
                mContext.getDir("phz", Context.MODE_PRIVATE).toString()
            )
        )
        //文件创建模式：与openFileOutput一起使用，如果文件已经存在，则将数据写入现有文件的末尾而不是擦除它。
        // mContext.openFileOutput("phz", Context.MODE_APPEND)
    }
}