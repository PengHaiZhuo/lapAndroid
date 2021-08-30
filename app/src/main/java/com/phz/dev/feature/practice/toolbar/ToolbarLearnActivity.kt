package com.phz.dev.feature.practice.toolbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.dev.R
import com.phz.dev.databinding.ActivityToolbarLearnBinding

/**
 * @author phz
 * @description
 */
class ToolbarLearnActivity : AppCompatActivity() {
    val mViewModel = viewModels<ToolbarLearnViewModel>()
    lateinit var binding: ActivityToolbarLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* binding=ActivityToolbarLearnBinding.inflate(layoutInflater)
         setContentView(binding.root)*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_toolbar_learn)
        initSupportActionBar()
        initObservable()
    }

    private fun initObservable() {
        mViewModel.value.isCollect.observe(this) {
            binding.mToolbar.menu.findItem(R.id.action_collect).icon = if (it) {
                resources.getDrawable(R.drawable.ic_collect, null)
            } else {
                resources.getDrawable(R.drawable.ic_not_collect, null)
            }
        }
    }

    private fun initSupportActionBar() {
        setSupportActionBar(binding.mToolbar)
        //设置了actionbar后，设置toolbar的title就需要使用supportActionBar来，使用toolbar.title设置不会生效
        supportActionBar?.apply {
            title = "One"
            subtitle = "Second"
            //向用户显示选择 home 将返回上一级而不是应用程序的顶层。
            setDisplayHomeAsUpEnabled(true)
            //显示标题/副标题（如果存在）
            setDisplayShowTitleEnabled(true)
        }
        binding.mToolbar.apply {
            //设置文本居中
            isTitleCentered = false
            isSubtitleCentered = false
        }
        immersionBar {
            titleBar(binding.mToolbar)
            statusBarColor(R.color.colorPrimary)
            autoStatusBarDarkModeEnable(true)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //设置膨胀菜单
        menuInflater.inflate(R.menu.toolbar_learn, menu)
        if (menu is MenuBuilder){
            val menuBuilder=menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
        }
        menu!!.forEachIndexed { _, item ->
            when (item.itemId) {
                R.id.action_search -> {
                    val searchView = item.actionView as SearchView
                    searchView.setIconifiedByDefault(false)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            //搜索文本提交回调
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            //文本变化回调
                            return true
                        }
                    })
                }
                R.id.action_collect -> {
                    if (mViewModel.value.isCollect.value) {
                        item.icon = resources.getDrawable(R.drawable.ic_collect, null)
                    } else {
                        item.icon = resources.getDrawable(R.drawable.ic_not_collect, null)
                    }
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                true
            }
            R.id.action_scan -> {
                true
            }
            R.id.action_collect -> {
                mViewModel.value.isCollect.value = !mViewModel.value.isCollect.value
                true
            }
            else -> {
                false
            }
        }
    }
}