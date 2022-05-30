package com.phz.dev.feature.practice.socket.udp

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.phz.common.ext.logE
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityUdpServerBinding
import com.phz.dev.feature.practice.socket.udp.adapter.MsgListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.net.DatagramPacket
import java.net.DatagramSocket

const val SERVER_PORT = 32108

class UdpServerActivity :BaseToolbarActivity<NoViewModel,ActivityUdpServerBinding>(){
    private val bufServer = ByteArray(BUF_LENGTH)
    private val msgSendAdapter=MsgListAdapter()
    private var msgSendList=ArrayList<String>()

    private lateinit var dsServer:DatagramSocket
    private lateinit var dpServerSend:DatagramPacket
    private lateinit var dpServerReceive:DatagramPacket

    private var flagServer=true

    override fun initData() {
        mViewDataBinding.rvSend.apply {
            vertical()
            adapter = msgSendAdapter
        }
        msgSendAdapter.submitList(msgSendList)
        //开启服务
        startServer()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Udp Server"
    }


    //开启服务
    fun startServer(){
        dsServer= DatagramSocket(SERVER_PORT)
        dpServerReceive= DatagramPacket(bufServer,BUF_LENGTH)
        lifecycleScope.launch(Dispatchers.IO){
            while (flagServer){
                dsServer.receive(dpServerReceive)
//                val strReceive=String(dpServerReceive.data,0,dpServerReceive.length)
                val strReceive=createReceiveData(dpServerReceive)
                strReceive.logE("收到信息")
                withContext(Dispatchers.Main){
                    msgSendList.add(strReceive)
                    msgSendAdapter.notifyDataSetChanged()
                }
                //发送回包
//                val strSend="服务端收到客户端消息"
//                dpServerSend= DatagramPacket(strSend.toByteArray(),strSend.length,dpServerReceive.address,dpServerReceive.port)
                val arrSend=createSendData("服务端收到客户端消息")
                dpServerSend= DatagramPacket(arrSend,arrSend.size,dpServerReceive.address,dpServerReceive.port)
                dsServer.send(dpServerSend)
                dpServerReceive.length=BUF_LENGTH
            }
            dsServer.close()
        }
    }

    /**
     * 将中文字符转码发送
     *
     * @param strSend
     */
    private fun createSendData(strSend: String): ByteArray {
        val baos = ByteArrayOutputStream()
        val dataStream = DataOutputStream(baos)
        try {
            dataStream.writeUTF(strSend)
            dataStream.close()
            return baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }

    /**
     * 将中文解码接收
     *
     * @param dp
     */
    private fun createReceiveData(dp: DatagramPacket): String {
        var result=""
        val stream = DataInputStream(
            ByteArrayInputStream(
                dp.data,
                dp.offset, dp.length
            )
        )
        try {
            result=stream.readUTF()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        flagServer=false
    }
}