package com.phz.dev

/**
 * @author phz on 2021/12/9
 * @description TheShy来全杀了
 */
fun main() {
    test_split()
}

fun test_split() {
    val str = "21,,2,5,7,,,6,,5"
    val arr = str.split(",").toTypedArray()
    arr.forEach {
        println(it)
    }
}