package com.phz.dev.feature.practice.animation.lottie.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.phz.dev.R

/**
 * @author phz on 2021/9/14
 * @description
 */
class DionaDialog(val mContext: Context) : Dialog(mContext, R.style.DialogTrans) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_lottie_diona)
    }
}