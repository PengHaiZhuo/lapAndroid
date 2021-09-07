package com.phz.dev.feature.practice.countdown

import com.phz.common.ext.logE
import java.util.concurrent.CountDownLatch

/**
 * @author phz on 2021/9/7
 * @description
 */
class Worker(startSignal: CountDownLatch, endSignal: CountDownLatch) : Runnable {

    private var mStartSignal: CountDownLatch = startSignal
    private var mEndSignal: CountDownLatch = endSignal

    override fun run() {
        try {
            mStartSignal.await() //当前线程在startSignal处等待，直到计数为0后继续执行
            doWork() //准备工作完成，工人开始工作
            mEndSignal.countDown() //一个工人完成工作，计数减一
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun doWork() {
        "工人工作中，工号为:${Thread.currentThread().name}".logE()
        Thread.sleep(1000)//模拟工作耗时
    }
}
