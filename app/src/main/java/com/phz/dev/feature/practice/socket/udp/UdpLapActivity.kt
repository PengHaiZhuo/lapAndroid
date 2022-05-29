package com.phz.dev.feature.practice.socket.udp

import android.os.Bundle
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityUdpLapBinding
import com.phz.dev.feature.practice.socket.udp.adapter.MsgListAdapter

class UdpLapActivity :BaseToolbarActivity<UdpLapViewModel,ActivityUdpLapBinding>(){
    private val msgSendAdapter=MsgListAdapter()
    private val msgReceiveAdapter=MsgListAdapter()

    override fun initData() {
        mViewDataBinding.rvSend.apply {
            vertical()
            adapter = msgSendAdapter
        }
        mViewDataBinding.rvReceive.apply {
            vertical()
            adapter = msgReceiveAdapter
        }
        mViewModel.msgSend.observe(this){
            msgSendAdapter.submitList(it)
        }
        mViewModel.msgReceive.observe(this){
            msgReceiveAdapter.submitList(it)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Udp Socket"
        mViewDataBinding.clickProxy=ClickProxy()
    }

    inner class ClickProxy{
        //发送消息
        fun send(){

        }
    }
}