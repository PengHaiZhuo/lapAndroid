package com.phz.dev.util

/**
 * @author phz
 * @description
 */
class StringUtil {
    companion object{

        @JvmStatic
        fun isUserName(username:String):Boolean{
            return !(username.isEmpty()||username.length<3)
        }

        @JvmStatic
        fun isPwd(pwd:String):Boolean{
            return !(pwd.isEmpty()||pwd.length<6||pwd.length>20)
        }
    }
}