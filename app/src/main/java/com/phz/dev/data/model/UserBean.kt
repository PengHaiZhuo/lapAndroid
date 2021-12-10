package com.phz.dev.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author phz on 2021/12/10
 * @description 用户bean
 */
@Parcelize
class UserBean(var admin: Boolean = false,
               var chapterTops: List<String> = listOf(),
               var collectIds: MutableList<String> = mutableListOf(),
               var email: String="",
               var icon: String="",
               var id: String="",
               var nickname: String="",
               var password: String="",
               var token: String="",
               var type: Int =0,
               var username: String="") : Parcelable