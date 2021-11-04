package com.phz.dev.feature.practice.list.recycleview.model

import androidx.annotation.DrawableRes

/**
 * @author phz on 2021/9/29
 * @description
 */
data class GenShinRole(val id:Int, val name:String, val des:String, @DrawableRes val imgResId:Int,val isLike:Boolean)