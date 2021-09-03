package com.phz.dev.feature.practice.popupwindow.normal

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.PopupWindow
import com.phz.common.ext.layoutInflater
import com.phz.dev.databinding.PopupOverFlowMenuBinding

/**
 * @author phz
 * @description 更多-菜单弹出框
 */
class OverFlowPopupWindow :PopupWindow{
    var mViewDataBinding: PopupOverFlowMenuBinding?=null
    var mClickProxy:ClickProxy?=null

    constructor(context:Context,clickProxy: ClickProxy):super(context){
        this.mClickProxy=clickProxy
        context.layoutInflater?.let {
            mViewDataBinding=PopupOverFlowMenuBinding.inflate(it)
        }
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.contentView=mViewDataBinding?.root
        this.isOutsideTouchable=true
        this.isFocusable=true
        mClickProxy?.apply {
            mViewDataBinding?.let {
                it.llNew.setOnClickListener {
                    mClickProxy!!.newProject()
                }
                it.llScan.setOnClickListener {
                    mClickProxy!!.scan()
                }
                it.llMap.setOnClickListener {
                    mClickProxy!!.map()
                }
            }
        }
    }
}

interface ClickProxy{
    fun newProject()
    fun scan()
    fun map()
}