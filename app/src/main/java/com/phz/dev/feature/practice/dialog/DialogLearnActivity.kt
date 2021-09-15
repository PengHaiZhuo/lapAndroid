package com.phz.dev.feature.practice.dialog

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityDialogLearnBinding
import com.phz.dev.feature.practice.dialog.activity.DialogSampleActivity
import com.phz.dev.feature.practice.dialog.fragment.DialogSampleFragment

/**
 * @author phz on 2021/9/14
 * @description 参考【https://developer.android.com/guide/topics/ui/dialogs?hl=zh-cn】
 * ①普通Dialog（标题+正文+按钮）传统单选列表、永久单选列表、永久多选列表
 * ②DialogFragment
 * ③Activity样式
 */
class DialogLearnActivity : BaseVmDbActivity<BaseViewModel, ActivityDialogLearnBinding>() {
    private val dialogFragment by lazy { DialogSampleFragment() }
    private var normalAlertDialog: AlertDialog? = null
    private var itemsAlertDialog: AlertDialog? = null
    private var singleChoiceItemsAlertDialog: AlertDialog? = null
    private var multiItemsAlertDialog: AlertDialog? = null

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
                setPositiveButton(R.string.confirm) { dialog, _ ->
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
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.click = ClickProxy()
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

        fun clickCustomView(){

        }
    }
}