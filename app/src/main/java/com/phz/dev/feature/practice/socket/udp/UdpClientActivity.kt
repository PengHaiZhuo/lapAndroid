package com.phz.dev.feature.practice.socket.udp

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.phz.common.ext.logE
import com.phz.common.ext.toHex
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityUdpClientBinding
import com.phz.dev.feature.practice.socket.udp.adapter.MsgListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.ByteString.Companion.decodeHex
import java.io.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*
import kotlin.collections.ArrayList

const val CLIENT_PORT = 8000 //客户端端口
const val BUF_LENGTH = 1024
//const val TIME_OUT = 5000 //设置接收数据的超时时间
//const val MAX_NUM = 5 //设置重发数据的最多次数

class UdpClientActivity :BaseToolbarActivity<NoViewModel,ActivityUdpClientBinding>(){
    private val bufClient = ByteArray(BUF_LENGTH)
    private val msgReceiveAdapter=MsgListAdapter()
    private var msgReceiveList=ArrayList<String>()

    private lateinit var dsClient:DatagramSocket
    private lateinit var dpClientSend:DatagramPacket
    private lateinit var dpClientReceive:DatagramPacket

    private var flagClient=true //控制客户端监听循环

    override fun initData() {
        mViewDataBinding.rvReceive.apply {
            vertical()
            adapter = msgReceiveAdapter
        }
        msgReceiveAdapter.submitList(msgReceiveList)
        //客户端在CLIENT_PORT端口监听数据
        dsClient= DatagramSocket(CLIENT_PORT)
        //初始化客户端用来接收数据的实例
        dpClientReceive= DatagramPacket(bufClient,BUF_LENGTH)
        //设置接收数据时阻塞最长时间
//        dsClient.soTimeout= TIME_OUT
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Udp Client"
        mViewDataBinding.clickProxy=ClickProxy()
    }

    inner class ClickProxy{
        //发送消息
        fun send(){
            lifecycleScope.launch(Dispatchers.IO){
                val strSend="客户端信息"
                val arrSend= arrayOf(43.toByte(),43.toByte(),27.toByte(),21.toByte()).toByteArray()

//                val arrSend=createSendData("客户端信息")
//                var clientAddress=InetAddress.getLocalHost()
                    var clientAddress=InetAddress.getByName("192.168.0.107")
//                dpClientSend= DatagramPacket(strSend.toByteArray(),strSend.length,clientAddress,SERVER_PORT)
                dpClientSend= DatagramPacket(arrSend,arrSend.size,clientAddress,SERVER_PORT)
                    dsClient.send(dpClientSend)
                while (flagClient){
                    dsClient.receive(dpClientReceive)
                    val receiveStr=dpClientReceive.data.toHex()
                    receiveStr.logE("收到信息")
                    receiveStr.length.toString().logE("字节数")
//                    val strReceive=String(dpClientReceive.data,0,dpClientReceive.length)
//                    val strReceive=createReceiveData(dpClientReceive)
//                    strReceive.logE("收到信息")
//                    withContext(Dispatchers.Main){
//                        msgReceiveList.add(strReceive)
//                        msgReceiveAdapter.notifyDataSetChanged()
//                    }
                }
                dsClient.close()
            }
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
        flagClient=false
    }
}