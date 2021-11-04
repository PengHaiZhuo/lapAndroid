package com.phz.dev.feature.practice.uriwithfilepath.bean


import com.google.gson.annotations.SerializedName

data class PathBean(
    @SerializedName("apiName")
    var apiName: String = "",
    @SerializedName("pathOrUri")
    var pathOrUri: String = ""
)