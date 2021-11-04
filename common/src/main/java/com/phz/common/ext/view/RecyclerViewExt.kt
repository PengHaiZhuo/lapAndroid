package com.phz.common.ext.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.phz.common.widget.recycleview.DefaultDecoration


/**
 * 纵向recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.vertical():RecyclerView{
    layoutManager = LinearLayoutManager(this.context)
    setHasFixedSize(true)
    return this
}

/**
 * 横向 recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.horizontal():RecyclerView{
    layoutManager = LinearLayoutManager(this.context).apply {
        orientation = RecyclerView.HORIZONTAL
    }
    setHasFixedSize(true)
    return this
}

/**
 * grid recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.grid(count:Int):RecyclerView{
    layoutManager = GridLayoutManager(this.context,count)
    setHasFixedSize(true)
    return this
}

/**
 * staggered grid recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.staggered(spanCount:Int=2,orientation:Int=RecyclerView.VERTICAL):RecyclerView{
    layoutManager = StaggeredGridLayoutManager(spanCount,orientation)
    setHasFixedSize(true)
    return this
}

/**
 * 设置分隔线
 */
fun RecyclerView.divider(block: DefaultDecoration.() ->Unit):RecyclerView{
    val itemDecoration = DefaultDecoration(context).apply(block)
    addItemDecoration(itemDecoration)
    return this
}

