package com.phz.dev.feature.main.mine

import androidx.lifecycle.viewModelScope
import com.phz.common.appContext
import com.phz.common.databinding.observablefield.IntObservableField
import com.phz.common.databinding.observablefield.StringObservableField
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.ext.request
import com.phz.dev.net.apiService
import com.phz.dev.util.PersistenceUtil
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/8/20
 * @description
 */
class MineViewModel : BaseViewModel() {
    //姓名
    var name = StringObservableField(appContext.getString(R.string.plz_login))

    //积分
    var integral = IntObservableField(0)

    //信息
    var info = StringObservableField("id：-　排名：- ")

    //头像url
    val imgUrl = R.drawable.ic_saber

    //gif头像
    val gifUrl = R.raw.xiangling
}