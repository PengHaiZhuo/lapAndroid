package com.phz.dev.feature.practice.countdown

import android.os.Bundle
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.databinding.ActivityCountDownLatchLearnBinding
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CountDownLatchLearnActivity :
    BaseVmDbActivity<BaseViewModel, ActivityCountDownLatchLearnBinding>() {

    companion object {
        const val WorkerNum = 5  //工人数量
    }

    override fun initData() {
        Thread{
            val mStartSignal = CountDownLatch(1)
            val mEndSignal = CountDownLatch(WorkerNum)
            for (i in 0 until WorkerNum) {
                i.toString().logE()
                Thread(Worker(mStartSignal,mEndSignal)).start()
            }
            "准备工作进行中".logE()//做一些准备工作
            mStartSignal.countDown()//准备工作完成，count=1的mStartSignal执行-1，为0后释放所有等待线程
            "准备工作已完成".logE()
            mEndSignal.await()//等待所有工作都完成继续执行后续代码
            "所有工作已完成".logE()

        }.start()



//        val latch = CountDownLatch(1)
//        readCacheRx(null) // pass "data" to check when cache has data
//            .switchIfEmpty(readNetworkRx())
//            .flatMapCompletable { saveCacheRx(it) }
//            .doOnComplete { latch.countDown() }
//            .subscribeOn(Schedulers.io())
//            .subscribe()
//        latch.await()
    }

    override fun initView(savedInstanceState: Bundle?) {
    }


    private fun readCacheRx(data: String? = null): Maybe<String> {
        return if (data != null) {
            Maybe
                .just(data)
                .delay(100, TimeUnit.MILLISECONDS)
                .doOnSuccess { "read from cache: $data".logE() }
        } else {
            Maybe
                .empty<String>()
                .delay(100, TimeUnit.MILLISECONDS)
                .doOnComplete { "read from cache: $data".logE() }
        }
    }

    private fun readNetworkRx(data: String = "data"): Single<String> {
        return Single
            .just(data)
            .delay(100, TimeUnit.MILLISECONDS)
            .doOnSuccess { "read from network: $data".logE() }
    }

    private fun saveCacheRx(data: String): Completable {
        return Completable
            .fromAction {
                "saved to cache: $data".logE()
            }
            .delay(100, TimeUnit.MILLISECONDS)
    }
}