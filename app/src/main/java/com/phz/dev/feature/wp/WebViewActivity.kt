package com.phz.dev.feature.wp

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.phz.common.ext.view.gone
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityWebviewBinding

/**
 * @author phz
 * @description 用于浏览网页
 */
class WebViewActivity : BaseToolbarActivity<NoViewModel,ActivityWebviewBinding>() {
    private var url: String? = null
    private var title: String? = null
    private var assetPath: String? = null
    private lateinit var wb:WebView
    
    companion object {
        const val FLAG_URL = "URL"
        const val FLAG_TITLE = "TITLE"
        const val FLAG_ASSET = "ASSET"
    }

    override fun initView(savedInstanceState: Bundle?) {
        url = intent.getStringExtra(FLAG_URL)
        title = intent.getStringExtra(FLAG_TITLE)
        assetPath = intent.getStringExtra(FLAG_ASSET)
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = title?:""
        wb=mViewDataBinding.wb
        wb.apply {
            //不显示横向滑动条
            isHorizontalScrollBarEnabled = false
            //不显示纵向滑动条
            isVerticalScrollBarEnabled = false
            settings.apply {
                //设置是否启用javascript
                setJavaScriptEnabled(true);
                //是否支持缩放（这个是前提）
                setSupportZoom(false);
                //是否显示缩放工具（当上门那个成立，再设置这个，必要条件）
                builtInZoomControls = false;
                //设置此属性，可任意比例缩放
                useWideViewPort = false;
                //设置充满全屏
                loadWithOverviewMode = true;
                //把所有内容放到WebView组件等宽的一列中
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
                //设置字体默认大小
                /*webSettings.setDefaultFontSize(15);*/

                //设置不允许安全来源从不安全的位置加载内容起源，非常建议使用这个。
                //webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
                //不建议使用这个，从任何来源加载内容。5.1以上默认禁止了https和http混用 这是开启
                //webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

                //如下设置是解决Cross origin requests are only supported for protocol schemes: http, data, chrome, https.这种问题的。
                //webSettings.setAllowFileAccess(true);
                //webSettings.setAllowFileAccessFromFileURLs(true);
                //能否访问来自于任何源的文件标识的URL
                //webSettings.setAllowUniversalAccessFromFileURLs(true);

                //设置WebChromeClient
                webChromeClient=object :WebChromeClient(){
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        mViewDataBinding.pbProgress.progress = progress
                        if (progress==100){
                            mViewDataBinding.pbProgress.gone()
                        }
                    }
                }
            }


        }
        url?.let {
            wb.loadUrl(it)
        }
        assetPath?.let {
            wb.loadUrl(it)
        }
    }

    override fun initData() {
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            val backList = wb.copyBackForwardList()
            if (backList.currentIndex > 0) {
                wb.goBack()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        //清除缓存和历史，避免内存泄露
        wb.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
        }
        val parent=wb.parent as ViewGroup
        parent.removeView(wb)
        wb.destroy()
    }
}