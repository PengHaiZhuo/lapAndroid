package com.phz.dev.feature.practice.uriwithfilepath

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phz.dev.feature.practice.uriwithfilepath.bean.PathBean

/**
 * @author phz on 2021/10/15
 * @description
 */
class UriWithFilePathViewModel : ViewModel() {
    private val _pathList = MutableLiveData<List<PathBean>>(emptyList())
    val pathList: LiveData<List<PathBean>>
        get() = _pathList
}