package com.phz.common.net.support.data

/**
 * @author phz
 * @description 服务器返回数据基类
 * 一般情况下 json数据格式返回都有code、data、message，具体命名和数据结构看服务端
 * @notice 继承此类，重写各个方法，设置返回字段名，设置成功条件（比如getResponseCode为200代表请求成功）
 */
abstract class BaseJsonResponse<T> {
    abstract fun isSuccess():Boolean
    abstract fun getResponseData():T
    abstract fun getResponseCode():Int
    abstract fun getResponseMsg():String
}