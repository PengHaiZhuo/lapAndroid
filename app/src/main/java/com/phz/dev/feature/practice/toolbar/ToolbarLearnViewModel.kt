package com.phz.dev.feature.practice.toolbar

import androidx.lifecycle.ViewModel
import com.phz.common.livedata.BooleanLiveData

/**
 * @author phz on 2021/8/27
 * @description
 */
class ToolbarLearnViewModel:ViewModel() {
    //默认未收藏
    var isCollect=BooleanLiveData()
}
