package com.phz.dev.feature.practice.toolbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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
        val ivNavHeader =
            binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.iv_nav_header)
        circleImageUrlRes(ivNavHeader, R.drawable.ic_dio)
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
        //?????????actionbar????????????toolbar???title???????????????supportActionBar????????????toolbar.title??????????????????
        supportActionBar?.apply {
            title = ""
            subtitle = ""
            //????????????????????? home ???????????????????????????????????????????????????
            setDisplayHomeAsUpEnabled(true)
            //????????????/???????????????????????????
            setDisplayShowTitleEnabled(true)
        }
        binding.mToolbar.apply {
            //??????????????????
            isTitleCentered = true
            isSubtitleCentered = true
            setNavigationOnClickListener { onBackPressed() }
        }
        immersionBar {
            titleBar(binding.mToolbar)
            statusBarColor(R.color.colorPrimary)
            autoStatusBarDarkModeEnable(true)
        }
        //???????????? FragmentContainerView ?????????????????????????????? supportFragmentManager ?????????
//        navController = findNavController( R.id.nav_host_fragment)//this is error
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //??????????????????
        navController.addOnDestinationChangedListener { _, _, _ ->//3????????????????????? controller, destination, arguments
            //???????????? NavController ???????????????????????????????????????????????????
        }
        //?????????????????????????????????????????????????????????????????????????????????setOf(R.id.home, R.id.mine)??????
        // ?????????AppBarConfiguration????????????
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            drawerLayout = binding.drawerLayout,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        //????????????????????????????????????????????????
        binding.navView.setupWithNavController(navController)

        //???toolbarLayout?????????????????????
//        binding.collapsingToolbarLayout.setupWithNavController(binding.mToolbar,navController,appBarConfiguration)

        //???toolbar?????????????????????
        binding.mToolbar.setupWithNavController(navController, appBarConfiguration)

        //????????????????????????????????????
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initView() {
        initSupportActionBar()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.clBottomSheet)
        binding.fab.setOnClickListener {
            //??????????????????
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        binding.vm = mViewModel.value
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //??????????????????
        menuInflater.inflate(R.menu.toolbar_learn, menu)
        if (menu is MenuBuilder) {
            //todo use popup window,because the RestrictedApi label
            menu.setOptionalIconsVisible(true)
        }
        menu!!.forEachIndexed { _, item ->
            when (item.itemId) {
                R.id.action_search -> {
                    val searchView = item.actionView as SearchView
                    //????????????????????????
                    searchView.queryHint = resources.getString(R.string.plz_input_query)
                    searchView.setIconifiedByDefault(false)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            //????????????????????????
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            //??????????????????
                            return true
                        }
                    })
                    val searchAutoComplete =
                        searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
                    val searchTextArr =
                        arrayOf("RNG", "EDG", "IG", "RW", "OMG", "C9", "DK", "DRX", "FNC","FPX","V5","LGD","LNG","WE")
                    val searchTextAdapter = ArrayAdapter<String>(
                        baseContext,
                        R.layout.item_simple_text,
                        R.id.tv_item_simple,
                        searchTextArr
                    )
                    searchAutoComplete.setAdapter(searchTextAdapter)
                    searchAutoComplete.setOnItemClickListener { _, _, position, _ ->
                        searchView.clearFocus()
                        searchView.setQuery(searchTextAdapter.getItem(position),true)
                    }
                    //???????????????????????????????????????
                    searchAutoComplete.threshold=1
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