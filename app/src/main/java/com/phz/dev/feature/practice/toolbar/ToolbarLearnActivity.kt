package com.phz.dev.feature.practice.toolbar

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.phz.dev.R
import com.phz.dev.databinding.ActivityToolbarLearnBinding

/**
 * @author phz
 * @description
 */
class ToolbarLearnActivity :AppCompatActivity(){
    val mViewModel =viewModels<ToolbarLearnViewModel>()
    lateinit var mViewDataBinding:ActivityToolbarLearnBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mViewDataBinding=DataBindingUtil.setContentView(this, R.layout.activity_toolbar_learn)
        initSupportActionBar()
    }

    private fun initSupportActionBar(){
        setSupportActionBar(mViewDataBinding.mToolbar)
        //设置了actionbar后，设置toolbar的title就需要使用supportActionBar来，使用toolbar.title设置不会生效
        supportActionBar?.apply {
            title = "ToolBarLearn"
            subtitle="Second Title"
            //向用户显示选择 home 将返回上一级而不是应用程序的顶层。
            setDisplayHomeAsUpEnabled(true)
            //显示标题/副标题（如果存在）
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}