package com.phz.dev.feature.practice.list.recycleview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.ext.logE

/**
 * @author phz
 * @description 滑动监听，当recycleView用的LinearLayoutManager
 */
class BottomReachedListener(var onBottomReached: (sum:Int,moreNum:Int) -> Unit = {_,_->}):RecyclerView.OnScrollListener() {
    private var isTriggered = false

    fun reset() {
        isTriggered = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isTriggered) {
            return
        }
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            ?: error("No LinearLayoutManager")
        val totalItems = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        "totalItems:$totalItems    lastVisibleItem:$lastVisibleItem".logE()

        if (lastVisibleItem == totalItems - 1) {
            isTriggered = true
            onBottomReached(totalItems,totalItems-lastVisibleItem)
        }
    }

}