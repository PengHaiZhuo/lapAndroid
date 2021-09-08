package com.phz.dev.net.authenticator

import com.phz.dev.app.Constants
import com.tencent.mmkv.MMKV
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * @author phz
 * @description 处理okhttp中身份验证失败 http code 401
 */
class MyAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        //token失效，处理登录验证超时
        MMKV.defaultMMKV().encode(Constants.TOKEN, "")//设置缓存token字段为“”
        //todo 跳转登录页 或 重新获取token
        return response.request
    }
}