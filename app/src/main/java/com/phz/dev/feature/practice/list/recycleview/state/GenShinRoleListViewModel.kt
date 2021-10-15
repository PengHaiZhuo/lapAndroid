package com.phz.dev.feature.practice.list.recycleview.state

import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.feature.practice.list.recycleview.model.GenShinRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleListViewModel : BaseViewModel() {
    private val _roleList = MutableStateFlow<List<GenShinRole>>(emptyList())
    val roles :StateFlow<List<GenShinRole>> =_roleList

    init {
        _roleList.value=generateRoleList()
    }

    fun toggleLikeStatus(id:Int){
        _roleList.value=_roleList.value.map {
            if (it.id==id){
                val isLike=it.isLike
                it.copy(isLike = !isLike)
            }else{
                it
            }
        }
    }

    private fun generateRoleList() = listOf(
        GenShinRole(1, "空", "开挂的主角", R.drawable.ic_genshin_k,false),
        GenShinRole(2, "可莉", "火花骑士", R.drawable.ic_genshin_kl,false),
        GenShinRole(3, "刻晴", "璃月七星中的玉衡", R.drawable.ic_genshin_kq,false),
        GenShinRole(4, "钟离", "岩王爷", R.drawable.ic_genshin_zl,false),
        GenShinRole(5, "温蒂", "干正事的风神", R.drawable.ic_genshin_wd,false),
        GenShinRole(6, "莫娜", "穷的没钱吃饭的占星术士", R.drawable.ic_genshin_mn,false),
        GenShinRole(7, "优菈", "我老婆", R.drawable.ic_genshin_yl,true)
    )
}