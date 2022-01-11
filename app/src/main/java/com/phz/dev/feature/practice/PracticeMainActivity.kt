package com.phz.dev.feature.practice

import android.database.Cursor
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.dev.R
import com.phz.dev.data.room.AppDataBase
import com.phz.dev.data.room.bean.Practice
import com.phz.dev.databinding.ActivityPracticeMainBinding
import com.phz.dev.feature.practice.animation.dynamic.ViewPagerSimpleSliderActivity
import com.phz.dev.feature.practice.animation.lottie.LottieLearnActivity
import com.phz.dev.feature.practice.dialog.DialogLearnActivity
import com.phz.dev.feature.practice.list.listview.DropDownMenuTlActivity
import com.phz.dev.feature.practice.list.listview.StudentListActivity
import com.phz.dev.feature.practice.list.recycleview.GenShinRoleActivity
import com.phz.dev.feature.practice.map.baidu.BaiduMapViewActivity
import com.phz.dev.feature.practice.mlkit.scan.MlKitScanMenuActivity
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.activity.DropDownMenuActivity
import com.phz.dev.feature.practice.saf.StorageAccessFrameworkActivity
import com.phz.dev.feature.practice.screenrecord.ScreenRecordActivity
import com.phz.dev.feature.practice.toolbar.DrawerLayoutLearnActivity
import com.phz.dev.feature.practice.uriwithfilepath.UriWithFilePathActivity
import com.phz.dev.feature.practice.viewstub.ViewStubLearnActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseToolbarActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    private lateinit var mAdapter: PracticeListAdapter

    companion object {
        const val LOADER_ID = 1
    }

    private val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                applicationContext,
                PracticeContentProvider.URI, arrayOf(Practice.COLUMN_NAME),
                null, null, null
            )

        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            mViewModel.formatCursorData(data)
            //因为Loader会自动重新运行查询，比如从其他页面回来，这里我们将他关了
            LoaderManager.getInstance(this@PracticeMainActivity).destroyLoader(LOADER_ID)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            mViewModel.formatCursorData(null)
        }

    }

    override fun initData() {
        mAdapter = PracticeListAdapter(this::onClickItem)
        mViewDataBinding.rvPractice.apply {
            vertical()
            adapter = mAdapter
        }
        lifecycleScope.launch {
            mViewModel.practiceList.collect {
                mAdapter.submitList(it)
            }
        }

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, mLoaderCallbacks)
        mViewDataBinding.searchRepo.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val resultList =
                        AppDataBase.getInstance().practiceDao().getPracticeLike(v.text.toString())
                    withContext(Dispatchers.Main) {
                        mViewModel.practiceList.value=resultList
                    }
                }
                true
            }
            false
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "实践与练习"
    }

    private fun onClickItem(name: String) {
        when (name) {
            "Screen Record" -> {
                startKtxActivity<ScreenRecordActivity>()
            }
            "ViewPager Transformer" -> {
                startKtxActivity<ViewPagerSimpleSliderActivity>()
            }
            "Lottie" -> {
                startKtxActivity<LottieLearnActivity>()
            }
            "Scan" -> {
                startKtxActivity<MlKitScanMenuActivity>()
            }
            "DrawerLayout" -> {
                startKtxActivity<DrawerLayoutLearnActivity>()
            }
            "PopupWindow" -> {
                startKtxActivity<DropDownMenuActivity>()
            }
            "ViewStub" -> {
                startKtxActivity<ViewStubLearnActivity>()
            }
            "Dialog" -> {
                startKtxActivity<DialogLearnActivity>()
            }
            "ExpandableListView" -> {
                startKtxActivity<StudentListActivity>()
            }
            "DropDownList" -> {
                startKtxActivity<DropDownMenuTlActivity>()
            }
            "Rv Payload Use" -> {
                startKtxActivity<GenShinRoleActivity>()
            }
            "BaiduMapView" -> {
                startKtxActivity<BaiduMapViewActivity>()
            }
            "Path" -> {
                startKtxActivity<UriWithFilePathActivity>()
            }
            "Storage Access Framework" -> {
                startKtxActivity<StorageAccessFrameworkActivity>()
            }
        }

    }
}


