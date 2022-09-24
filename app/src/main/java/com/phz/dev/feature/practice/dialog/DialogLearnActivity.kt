package com.phz.dev.feature.practice.dialog

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlRes
import com.phz.common.databinding.MineBindingAdapter.imageGifUrlRes
import com.phz.common.ext.logE
import com.phz.common.ext.showSnackShort
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityDialogLearnBinding
import com.phz.dev.databinding.CustomFirstUseBinding
import com.phz.dev.databinding.CustomGenshinUserReflectBinding
import com.phz.dev.feature.practice.dialog.activity.DialogSampleActivity
import com.phz.dev.feature.practice.dialog.fragment.DialogSampleFragment
import com.phz.dev.feature.wp.WebViewActivity

/**
 * @author phz on 2021/9/14
 * @description 参考【https://developer.android.com/guide/topics/ui/dialogs?hl=zh-cn】
 * ①普通Dialog（标题+正文+按钮）传统单选列表、永久单选列表、永久多选列表
 * ②DialogFragment
 * ③Activity样式
 * ④自定义customView
 * 补充：推荐使用开源库👉https://github.com/afollestad/material-dialogs
 */
class DialogLearnActivity : BaseToolbarActivity<NoViewModel, ActivityDialogLearnBinding>() {
    private var normalAlertDialog: AlertDialog? = null
    private var itemsAlertDialog: AlertDialog? = null
    private var singleChoiceItemsAlertDialog: AlertDialog? = null
    private var multiItemsAlertDialog: AlertDialog? = null
    private val dialogFragment by lazy { DialogSampleFragment() }
    private var customViewAlertDialog: AlertDialog? = null
    private var customViewAlertDialogP: AlertDialog? = null

    override fun initData() {
        //普通Dialog
        val normalBuilder = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                setTitle("Normal Dialog")
                setMessage("this is normal dialog.")
                setPositiveButton(R.string.confirm) { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
        normalAlertDialog = normalBuilder.create()
        //Items
        var itemsBuilder = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                setTitle("Items Dialog")
                /*setItems(R.array.items_dialog_learn) { _, _ ->//dialog, which

                }*/
                val myStringArray = resources.getStringArray(R.array.items_dialog_learn)
                val adapter = ArrayAdapter(
                    this@DialogLearnActivity,
                    android.R.layout.simple_list_item_1,
                    myStringArray
                )
                //适配器只需要是ListAdapter即可
                setAdapter(adapter) { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
        itemsAlertDialog = itemsBuilder.create()
        //SingleChoiceItems
        var singleChoiceBuilder = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                setTitle("SingleChoiceItems Dialog")
                setSingleChoiceItems(R.array.items_dialog_learn, 0) { _, _ ->
                }
                setPositiveButton(R.string.confirm) { _, _ ->
                }
                setNegativeButton(R.string.cancel) { _, _ ->
                }
            }
        singleChoiceItemsAlertDialog = singleChoiceBuilder.create()
        //MultiItems
        var multiItemsBuilder = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                setTitle("MultiItems Dialog")
                setMultiChoiceItems(
                    R.array.items_dialog_learn,
                    null
                ) { _, _, _ ->//dialog, which, isChecked
                }
                setPositiveButton(R.string.confirm) { _, _ ->
                }
                setNegativeButton(R.string.cancel) { _, _ ->
                }
            }
        multiItemsAlertDialog = multiItemsBuilder.create()
        //Custom View
        val customBuilder = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                val binding = CustomGenshinUserReflectBinding.inflate(layoutInflater)
                setView(binding.root)
                imageGifUrlRes(binding.ivCustomGenshinRefBg, R.raw.xiangling)
                circleImageUrlRes(binding.ivCustomGenshinRefAvatar, R.drawable.ic_genshin_k)
                binding.btCustomGenshinUserRefConfirm.setOnClickListener {
                    "邮箱：${binding.tvInputCustomGenshinMail.text} 反馈：${binding.tvInputCustomGenshinReflect.text}".logE()
                    customViewAlertDialog?.dismiss()
                }
                binding.btCustomGenshinUserRefCancel.setOnClickListener {
                    customViewAlertDialog?.dismiss()
                }
            }
        customViewAlertDialog = customBuilder.create()
        //Custom View P
        val customBuilderP = AlertDialog.Builder(this@DialogLearnActivity)
            .apply {
                val binding = CustomFirstUseBinding.inflate(layoutInflater)
                setView(binding.root)
                setCancelable(false)
                //设置超链接点击
                binding.tvContent.apply {
                    movementMethod=LinkMovementMethod.getInstance()
                    setLinkTextColor(getColor(R.color.colorBlue66))
                }
                val ss=getString(R.string.tip_first_use)
                val privacy ="《隐私政策》"
                val agreement ="《用户协议》"
                val startIndexPrivacy=ss.indexOf(privacy)
                val endIndexPrivacy=startIndexPrivacy+privacy.length
                val startIndexAgreement =ss.indexOf(agreement)
                val endIndexAgreement =startIndexAgreement+agreement.length
                var spanString= SpannableString(ss)
                //设置span
                spanString.apply {
                    setSpan(object :ClickableSpan(){
                        override fun onClick(widget: View) {
                            var lists = arrayListOf(
                                Pair(WebViewActivity.FLAG_TITLE, getString(R.string.privacy)),
                                Pair(WebViewActivity.FLAG_ASSET, "file:///android_asset/privacy.html")
                            )
                            startKtxActivity<WebViewActivity>(values = lists)
                        }
                    },startIndexPrivacy,endIndexPrivacy,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    setSpan(object :ClickableSpan(){
                        override fun onClick(widget: View) {
                            var lists = arrayListOf(
                                Pair(WebViewActivity.FLAG_TITLE, getString(R.string.agreement)),
                                Pair(WebViewActivity.FLAG_ASSET, "file:///android_asset/agreement.html")
                            )
                            context!!.startKtxActivity<WebViewActivity>(values = lists)
                        }
                    },startIndexAgreement,endIndexAgreement,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                binding.tvContent.text=spanString
                binding.btAgree.setOnClickListener {
                    showSnackShort(getString(R.string.agree))
                    customViewAlertDialogP?.dismiss()
                }
                binding.btNoAgree.setOnClickListener {
                    showSnackShort(getString(R.string.no_agree))
                    customViewAlertDialogP?.dismiss()
                }
            }
        customViewAlertDialogP = customBuilderP.create()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.click = ClickProxy()
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Dialog"
    }

    inner class ClickProxy {
        fun clickNormal() {
            normalAlertDialog?.show()
        }

        fun clickItems() {
            itemsAlertDialog?.show()
        }

        fun clickSingleItems() {
            singleChoiceItemsAlertDialog?.show()
        }

        fun clickMultiItems() {
            multiItemsAlertDialog?.show()
        }

        fun clickFragment() {
            dialogFragment.show(supportFragmentManager, "fragmentD")
        }

        fun clickActivity() {
            startKtxActivity<DialogSampleActivity>()
        }

        fun clickCustomView() {
            customViewAlertDialog?.show()
        }

        fun clickCustomViewP() {
            customViewAlertDialogP?.show()
        }

    }
}