package com.phz.common.util

import android.content.Context
import android.location.LocationManager
import com.phz.common.ext.locationManager


/**
 * @author phz on 2021/10/12
 * @description
 */
object LocationUtil {
    /**
     * 是否打开Gps
     * 国内大多数定制手机Gps与wifi
     */
    @JvmStatic
    fun isGpsEnable(mContext: Context): Boolean {
        val locManager = mContext.locationManager
        return locManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}