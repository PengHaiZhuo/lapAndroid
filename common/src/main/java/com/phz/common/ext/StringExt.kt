package com.phz.common.ext

import android.util.Log
import com.phz.common.appLogTag
import java.util.regex.Pattern

/**
 * @author phz
 * @description
 */
/**
 * 是否为手机号
 */
fun String?.isPhone(): Boolean {
    return this?.let {
        Pattern.matches(
            it,
            "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}\$"
        )
    } ?: let {
        false
    }
}

/**
 * 是否为座机号
 */
fun String?.isTel(): Boolean {
    return this?.let {
        val matcher1 = Pattern.matches("^0(10|2[0-5|789]|[3-9]\\d{2})\\d{7,8}\$", this)
        val matcher2 = Pattern.matches("^0(10|2[0-5|789]|[3-9]\\d{2})-\\d{7,8}$", this)
        val matcher3 = Pattern.matches("^400\\d{7,8}$", this)
        val matcher4 = Pattern.matches("^400-\\d{7,8}$", this)
        val matcher5 = Pattern.matches("^800\\d{7,8}$", this)
        val matcher6 = Pattern.matches("^800-\\d{7,8}$", this)
        return matcher1 || matcher2 || matcher3 || matcher4 || matcher5 || matcher6
    } ?: let {
        false
    }
}

/**
 * 是否为邮箱号
 */
fun String?.isEmail(): Boolean {
    return this?.let {
        Pattern.matches(this, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$")
    } ?: let {
        false
    }
}

/**
 * 是否是邮编
 */
fun String?.isZip(): Boolean {
    return this?.let {
        Pattern.matches(this, "^[1-9][0-9]{5}\$")
    } ?: let {
        false
    }
}

/**
 * 是否是银联卡  19位
 */
fun String?.isUnionPayCard(): Boolean {
    return this?.let {
        Pattern.matches(this, "^\\d{19}\$")
    } ?: let {
        false
    }
}

/**
 * 是否是信用卡  16位
 */
fun String?.isCreditCard(): Boolean {
    return this?.let {
        Pattern.matches(this, "^\\d{16}\$")
    } ?: let {
        false
    }
}

/**
 * 是否是中国身份证
 */
fun String?.isChineseIdCard(): Boolean {
    return this?.let {
        Pattern.matches(
            this,
            "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}\$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|[X|x])\$"
        )
    } ?: let {
        false
    }
}

/**
 * 是否是中国车牌
 */
fun String?.isChineseCarLicence(): Boolean {
    return this?.let {
        Pattern.matches(
            this,
            "^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳])" +
                    "|([A-Z]{2}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[0-9]{4}[A-HJ-NP-Z0-9])" +
                    "|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))$"
        )
    } ?: let {
        false
    }
}

/**
 * 字符串是否包含表情
 */
fun String?.isContainEmo(): Boolean {
    return this?.let {
        var emoji = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
        )
        emoji.matcher(it).find()
    } ?: let {
        false
    }
}

/**
 * 字节数组转hex string
 * @return 16进制字符串，不足2位会补0
 */
fun ByteArray.toHex() = joinToString("") { String.format("%02X", (it.toInt() and 0xff)) }

//stdlib包自带的扩展方法toByteArray
//fun String.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray = (this as java.lang.String).getBytes(charset)

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logV(tag: String = appLogTag) =
    log(LEVEL.V, tag, this)

fun String.logD(tag: String = appLogTag) =
    log(LEVEL.D, tag, this)

fun String.logI(tag: String = appLogTag) =
    log(LEVEL.I, tag, this)

fun String.logW(tag: String = appLogTag) =
    log(LEVEL.W, tag, this)

fun String.logE(tag: String = appLogTag) =
    log(LEVEL.E, tag, this)

private fun log(level: LEVEL, tag: String, message: String) {
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}