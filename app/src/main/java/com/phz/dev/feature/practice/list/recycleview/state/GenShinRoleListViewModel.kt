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
        GenShinRole(1, "阿贝多", "在雪山写生", R.drawable.ic_genshin_abd,false),
        GenShinRole(2, "芭芭拉", "大家的偶像", R.drawable.ic_genshin_bbl,false),
        GenShinRole(3, "菲谢尔", "断罪之皇女", R.drawable.ic_genshin_fxe,false),
        GenShinRole(4, "甘雨", "月海亭的秘书", R.drawable.ic_genshin_gy,false),
        GenShinRole(5, "胡桃", "往生堂七十七代堂主", R.drawable.ic_genshin_ht,false),
        GenShinRole(6, "空", "旅行者", R.drawable.ic_genshin_k,false),
        GenShinRole(7, "可莉", "火花骑士", R.drawable.ic_genshin_kl,true),
        GenShinRole(8, "刻晴", "刻师傅", R.drawable.ic_genshin_kq,false),
        GenShinRole(9, "莫娜", "占星术士", R.drawable.ic_genshin_mn,false),
        GenShinRole(10, "诺艾尔", "岩石的重量让人安心", R.drawable.ic_genshin_nae,false),
        GenShinRole(11, "琴", "蒲公英骑士", R.drawable.ic_genshin_q,false),
        GenShinRole(12, "七七", "是个僵尸", R.drawable.ic_genshin_qq,false),
        GenShinRole(13, "神里绫华", "社奉行神里家大小姐", R.drawable.ic_genshin_sl,false),
        GenShinRole(14, "温蒂", "卖唱的", R.drawable.ic_genshin_wd,false),
        GenShinRole(15, "魈", "降魔大圣", R.drawable.ic_genshin_x,false),
        GenShinRole(16, "霄宫", "鸣神岛夏天的象征", R.drawable.ic_genshin_xg,false),
        GenShinRole(17, "心海", "反抗军领袖", R.drawable.ic_genshin_xh,false),
        GenShinRole(18, "香玲", "璃月大厨", R.drawable.ic_genshin_xl,false),
        GenShinRole(19, "莹", "爷真可爱", R.drawable.ic_genshin_y,false),
        GenShinRole(20, "烟绯", "璃月律师", R.drawable.ic_genshin_yf,false),
        GenShinRole(21, "影", "雷电将军", R.drawable.ic_genshin_ying,false),
        GenShinRole(22, "优菈", "浪花骑士", R.drawable.ic_genshin_yl,true),
        GenShinRole(23, "钟离", "岩王爷", R.drawable.ic_genshin_zl,false),
    )
}