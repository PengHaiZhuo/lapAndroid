package com.phz.common.ext

import android.os.Build
import androidx.annotation.StringDef

/**
 * @author phz
 * @description 手机信息
 */
/**
 * 获取手机型号
 */
fun getPhoneModel(): String{
    return Build.MODEL
}

/**
 * 获取手机品牌
 */
fun getPhoneBrand(): String{
    return Build.BRAND
}


@StringDef(BRAND.XIAOMI, BRAND.HUAWEI,BRAND.HUAWEI_HONOR,BRAND.OPPO,BRAND.VIVO
    ,BRAND.ONEPLUS,BRAND.HTC,BRAND.MEIZU,BRAND.SAMSUNG,BRAND.SONY,BRAND.GIONEE,BRAND.COOLPAD,BRAND.NUBIA,BRAND.NOKIA)
@Retention(AnnotationRetention.SOURCE)
annotation class BRAND{
    //Build.BRAND.toUpperCase()
    companion object {
        //小米
        const val XIAOMI="XIAOMI"
        //华为
        const val HUAWEI="HUAWEI"
        //华为荣耀
        const val HUAWEI_HONOR="HONOR"
        //oppo
        const val OPPO="OPPO"
        //vivo
        const val VIVO="VICO"

        //一加
        const val ONEPLUS="ONEPLUS"
        //htc
        const val HTC="HTC"
        //魅族
        const val MEIZU="MEIZU"
        //三星
        const val SAMSUNG="SAMSUNG"
        //索尼
        const val SONY="SONY"
        //金立
        const val GIONEE="GIONEE"
        //酷派
        const val COOLPAD="COOLPAD"
        //努比亚
        const val NUBIA="NUBIA"
        //诺基亚
        const val NOKIA="NOKIA"
    }
}