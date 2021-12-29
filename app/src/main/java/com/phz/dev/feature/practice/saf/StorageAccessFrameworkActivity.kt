package com.phz.dev.feature.practice.saf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivitySafBinding

/**
 * @author phz on 2021/10/19
 * @description Storage Access Framework，存储访问框架使用
 */
class StorageAccessFrameworkActivity : BaseVmDbActivity<NoViewModel, ActivitySafBinding>() {

    companion object {
        val IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val IMAGE_PROJECTION = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media._ID
        )
        val IMAGE_SELECTION =
            MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?"
        val IMAGE_SELECTION_ARGS = arrayOf("image/jpeg", "image/png")
        val IMAGE_SORTORDER = MediaStore.Images.Media.DATE_MODIFIED + " desc"
    }

    //startActivityForResult被标记为过时，我换成这种方式了
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                //通过intent获取选中图片uri，SAF貌似只能选一张图...
                val uri = result.data?.data ?: return@registerForActivityResult
                uri.toString().logE()
                // 获取图片信息
//            val cursor=this.contentResolver.query(IMAGE_URI,IMAGE_PROJECTION,IMAGE_SELECTION,IMAGE_SELECTION_ARGS,IMAGE_SORTORDER,null)
                val cursor = this.contentResolver.query(uri, null, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    //根据uri处理图片
                }
                mViewDataBinding.tvContent.text = uri.toString()
            }
        }

    override fun initData() {
        mViewDataBinding.clickProxy = ProxyClick()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "存储访问框架"
    }

    inner class ProxyClick {
        //Image
        fun clickImage() {
            val intent = Intent()
            intent.apply {
                action = Intent.ACTION_OPEN_DOCUMENT
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            resultLauncher.launch(intent)
        }
    }
}