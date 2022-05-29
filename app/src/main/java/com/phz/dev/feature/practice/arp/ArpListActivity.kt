package com.phz.dev.feature.practice.arp

import android.annotation.TargetApi
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityArpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress

/**
 * @author phz
 * @description 显示arp列表
 */
class ArpListActivity : BaseToolbarActivity<NoViewModel,ActivityArpBinding>() {
    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Arp List"
        mViewDataBinding.clickProxy = ProxyClick()
    }
    inner class ProxyClick {
        //读取arp文件
        fun read(){
            val runtime = Runtime.getRuntime()
            val proc = runtime.exec("ip neigh show")
            proc.waitFor()
            val br = BufferedReader(InputStreamReader(proc.inputStream))
//            val br = BufferedReader(FileReader("/proc/net/arp"))
            var line: String
            runCatching {
                while (br.readLine().also { line = it } != null) {
                    val clientInfo = line.split(" ").toTypedArray()
                    clientInfo.toString().logE()
                    //192.168.0.1 dev wlan0 lladdr 80:ea:07:90:fc:bd REACHABLE
                    if (!clientInfo[3].equals("type", ignoreCase = true)) {
                        val mac = clientInfo[4]
                        val ip = clientInfo[0]
                        mViewDataBinding.textView.append("\n\nip: $ip Mac: $mac")
                    }
                }

            }
        }
        //尝试访问某个IP 192.168.1.100
        fun send(){
            mViewModel.viewModelScope.launch(Dispatchers.IO) {
                //该方法向地址发送一个 ARP，然后发一个ICMP ping数据包
                InetAddress.getByName("192.168.0.100")?.isReachable(2000)
            }
        }
    }

}