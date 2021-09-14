package com.phz.dev.feature.practice.dialog

import android.os.Bundle
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.databinding.ActivityDialogLearnBinding

/**
 * @author phz on 2021/9/14
 * @description 参考【https://developer.android.com/guide/topics/ui/dialogs?hl=zh-cn】
 * ①普通Dialog（标题+正文+按钮）
 * ②DialogFragment 传统单选列表、永久单选列表、永久多选列表
 * ③Activity样式
 */
class DialogLearnActivity : BaseVmDbActivity<BaseViewModel, ActivityDialogLearnBinding>() {
    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}