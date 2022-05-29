package com.phz.dev.feature.practice.socket.udp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UdpLapViewModel:ViewModel() {
    val msgSend=MutableLiveData<ArrayList<String>>()
    val msgReceive=MutableLiveData<ArrayList<String>>()
}