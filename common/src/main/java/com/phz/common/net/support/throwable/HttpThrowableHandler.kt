package com.phz.common.net.support.throwable

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import java.net.ConnectException

/**
 * @author phz
 * @description http错误信息处理类
 */
object HttpThrowableHandler {
    fun handleException(e: Throwable?): HttpException {
        val ex: HttpException
        e?.let {
            when(it){
                is HttpException ->{
                    ex=HttpException(NetError.NETWORK_ERROR,e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = HttpException(NetError.PARSE_ERROR,e)
                    return ex
                }
                is ConnectException -> {
                    ex = HttpException(NetError.CONNECT_ERROR,e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = HttpException(NetError.SSL_ERROR,e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = HttpException(NetError.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = HttpException(NetError.NO_HOST_ERROR,e)
                    return ex
                }
                is HttpException -> return it
                else -> {
                    ex=HttpException(NetError.UNKNOWN,e)
                    return ex
                }
            }

        }
        ex = HttpException(NetError.UNKNOWN,e)
        return ex
    }

}