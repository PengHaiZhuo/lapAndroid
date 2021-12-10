package com.phz.dev.util

import com.phz.dev.data.model.UserBean
import com.tencent.mmkv.MMKV

/**
 * @author phz
 * @description 数据持久化工具类
 */
object PersistenceUtil {
    private const val ID_CACHE = "cache_kk"

    const val USERNAME = "USERNAME"
    const val PWD = "PWD"

    //登陆后获取的token
    const val TOKEN = "TOKEN"

    //登录后获取的USER对象
    const val USER = "USER"


    fun clear() {
        setUserName("")
        setPWD("")
        setToken("")
        setUserBean(UserBean())
    }

    fun setUserName(name: String) {
        MMKV.mmkvWithID(ID_CACHE).encode(USERNAME, name)
    }

    fun getUserName(): String = MMKV.mmkvWithID(ID_CACHE).decodeString(USERNAME, "")

    fun setPWD(pwd: String) {
        MMKV.mmkvWithID(ID_CACHE).encode(PWD, pwd)
    }

    fun getPWD(): String = MMKV.mmkvWithID(ID_CACHE).decodeString(PWD, "")

    fun setToken(token: String) {
        MMKV.mmkvWithID(ID_CACHE).encode(TOKEN, token)
    }

    fun getToken(): String = MMKV.mmkvWithID(ID_CACHE).decodeString(TOKEN)

    fun setUserBean(userBean: UserBean) {
        MMKV.defaultMMKV().encode(USER, userBean)
    }

    fun getUserBean(): UserBean? =
        MMKV.defaultMMKV().decodeParcelable(USER, UserBean::class.java, null)
}