package com.phz.dev.api.service

import com.phz.dev.data.model.UserBean
import com.phz.dev.net.data.bean.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * @author phz
 * @description 网络Api接口文档类
 */
interface WanAndroidApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ):BaseResponse<UserBean>

    @GET("user/logout/json")
    suspend fun logout():BaseResponse<String?>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") repwd: String
    ):BaseResponse<UserBean>
}