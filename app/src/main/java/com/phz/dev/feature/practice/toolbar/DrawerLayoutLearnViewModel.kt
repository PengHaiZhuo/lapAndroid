package com.phz.dev.feature.practice.toolbar

import androidx.lifecycle.ViewModel
import com.phz.common.livedata.BooleanLiveData
import com.phz.common.livedata.IntLiveData
import com.phz.dev.R

/**
 * @author phz on 2021/8/27
 * @description
 */
class DrawerLayoutLearnViewModel:ViewModel() {
    //默认未收藏
    var isCollect=BooleanLiveData()

    var avatarId=IntLiveData()

    init{
        avatarId.value= R.drawable.ic_dio
    }
}
