package com.phz.dev.feature.practice.toolbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlRes
import com.phz.common.ext.logE
import com.phz.dev.R
import com.phz.dev.databinding.ActivityDrawerLayoutLearnBinding

/**
 * @author phz
 * @description
 */
class DrawerLayoutLearnActivity : AppCompatActivity() {
    private val mViewModel = viewModels<DrawerLayoutLearnViewModel>()
    lateinit var binding: ActivityDrawerLayoutLearnBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* binding=ActivityDrawerLayoutLearnBinding.inflate(layoutInflater)
         setContentView(binding.root)*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer_layout_learn)
        initView()
        initObservable()
        initData()
    }

    private fun initData() {
        //directly findViewById will get null,use getHeaderView
        val ivNavHeader=binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.iv_nav_header)
        circleImageUrlRes(ivNavHeader,R.drawable.ic_dio)
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
            title = ""
            subtitle = ""
            //向用户显示选择 home 将返回上一级而不是应用程序的顶层。
            setDisplayHomeAsUpEnabled(true)
            //显示标题/副标题（如果存在）
            setDisplayShowTitleEnabled(true)
        }
        binding.mToolbar.apply {
            //设置文本居中
            isTitleCentered = true
            isSubtitleCentered = true
            setNavigationOnClickListener { onBackPressed() }
        }
        immersionBar {
            titleBar(binding.mToolbar)
            statusBarColor(R.color.colorPrimary)
            autoStatusBarDarkModeEnable(true)
        }
        //目前使用 FragmentContainerView 不是很友好，你必须从 supportFragmentManager 访问它
//        navController = findNavController( R.id.nav_host_fragment)//this is error
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController =navHostFragment.navController
        //监听导航事件
        /**
         * 3个占位符分别为 controller, destination, arguments
         */
        navController.addOnDestinationChangedListener {_,_,_ ->
            //该接口在 NavController 的当前目的地或其参数发生更改时调用
        }
        //一般情况下只有一个顶层目的地，如果有多个顶级，需要传入setOf(R.id.home, R.id.mine)这种
        // 具体看AppBarConfiguration构造方法
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            drawerLayout = binding.drawerLayout,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        //将抽屉布局中导航栏添加到导航支持
        binding.navView.setupWithNavController(navController)

        //将toolbarLayout添加到导航支持
//        binding.collapsingToolbarLayout.setupWithNavController(binding.mToolbar,navController,appBarConfiguration)

        //将toolbar添加到导航支持
        binding.mToolbar.setupWithNavController(navController,appBarConfiguration)

        //向默认操作栏添加导航支持
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initView() {
        initSupportActionBar()
        binding.fab.setOnClickListener {
            Snackbar.make(it,"Click fab",Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //设置膨胀菜单
        menuInflater.inflate(R.menu.toolbar_learn, menu)
        if (menu is MenuBuilder) {
            val menuBuilder = menu as MenuBuilder
            //todo use popup window,because the RestrictedApi label
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
        item.itemId.toString().logE("onOptionsItemSelected")
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
                val navController = findNavController(R.id.nav_host_fragment)
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }
}