package com.phz.common.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author phz
 * @description okhttp网络请求管理类基类。子类可继承此类，重写[setHttpClientBuilder]和[setRetrofitBuilder]
 * 或者采取其他方式初始化retrofit和OkHttpClient
 */
abstract class HttpCommonManager {

    /**
     * <T> 某个特定的ApiService类
     * @notice 返回结果之前，创建了Retrofit，创建了okHttpClient
     */
    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
        return setRetrofitBuilder(retrofitBuilder).build().create(serviceClass)
    }

    /**
     * 子类实现后可以对 OkHttpClient.Builder 做任意操作，比如添加拦截器，设置超时时间等
     */
    abstract fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder

    /**
     * 子类实现后可以对 Retrofit.Builder 做任意操作，比如添加解析器和回调库
     */
    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    private val okHttpClient : OkHttpClient
    get() {
        //看RetrofitUrlManager代码可知，给OkHttpClient.Builder添加了一个拦截器，满足条件这个拦截器会对Request做了一些操作，执行切换 BaseUrl 的相关逻辑
        /*var builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
        builder = setHttpClientBuilder(builder)
        return builder.build()*/

        return setHttpClientBuilder(OkHttpClient.Builder()).build()
    }
}