package com.phz.dev.feature.practice.arp

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
            mViewDataBinding.textView.text=""
            mViewModel.viewModelScope.launch(Dispatchers.IO) {
                val runtime = Runtime.getRuntime()
                val proc = runtime.exec("ip neigh show")
                proc.waitFor()
                val br = BufferedReader(InputStreamReader(proc.inputStream))
//            val br = BufferedReader(FileReader("/proc/net/arp"))
                var line: String=""
                runCatching {
                    while (br.ready()&&(br.readLine().also { line = it } != null)) {
                        val clientInfo = line.split(" ").toTypedArray()
                        if (clientInfo.size<5)return@launch
                        //192.168.0.1 dev wlan0 lladdr 80:ea:07:90:fc:bd REACHABLE
//                        if (!clientInfo[3].equals("type", ignoreCase = true)) {}
                            val info0=clientInfo[0]
                            val info1=clientInfo[1]
                            val info2=clientInfo[2]
                            val info3=clientInfo[3]
                            val info4=clientInfo[4]
                            "$info0  $info1  $info2  $info3  $info4".logE()

                            val mac = clientInfo[4]
                            val ip = clientInfo[0]
                            mViewDataBinding.textView.append("\n\nip: $ip Mac: $mac")
                    }
                }.onSuccess {
                    "成功了".logE()
                }.onFailure {
                    it.message?.logE()
                    "失败了".logE()
                }

            }
        }
        //尝试访问某个IP
        fun send(){
            mViewModel.viewModelScope.launch(Dispatchers.IO) {
                //该方法向地址发送一个 ARP，然后发一个ICMP ping数据包
                InetAddress.getByName("192.168.0.107")?.isReachable(2000)
            }
        }
    }

}