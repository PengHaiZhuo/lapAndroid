package com.phz.dev.feature.practice

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.page.adapter.listener.OnItemClickListener
import com.phz.dev.R
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

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseToolbarActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    private var mAdapter = PracticeAdapter()

    companion object {
        const val LOADER_ID=1
    }

    private val mLoaderCallbacks= object:LoaderManager.LoaderCallbacks<Cursor>{
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                applicationContext,
                PracticeContentProvider.URI, arrayOf(Practice.COLUMN_NAME),
                null, null, null
            )

        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            mAdapter.setPractices(data)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            mAdapter.setPractices(null)
        }

    }

    override fun initData() {
        mAdapter.setOnItemClick(object : OnItemClickListener<String> {
            override fun onClick(bean: String, position: Int) {
                when (bean) {
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
        })
        mViewDataBinding.rvPractice.apply {
            vertical()
            adapter = mAdapter
        }
        LoaderManager.getInstance(this).initLoader(LOADER_ID,null,mLoaderCallbacks)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "实践与练习"
    }

    private class PracticeAdapter : RecyclerView.Adapter<PracticeAdapter.ViewHolder>() {
        private var mCursor: Cursor? = null
        private var onItemClickListener: OnItemClickListener<String>? = null //item点击监听
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (mCursor!!.moveToPosition(position)) {
                holder.mText.text = mCursor!!.getString(
                    mCursor!!.getColumnIndexOrThrow(Practice.COLUMN_NAME)
                )
                val txt=holder.mText.text
                holder.mText.clickNoRepeat {
                    onItemClickListener?.onClick(txt.toString(),position)
                }
            }
        }

        override fun getItemCount(): Int {
            return if (mCursor == null) 0 else mCursor!!.count
        }

        /*设置item点击监听*/
        fun setOnItemClick(onClick: OnItemClickListener<String>){
            this.onItemClickListener=onClick
        }

        fun setPractices(cursor: Cursor?) {
            mCursor = cursor
            notifyDataSetChanged()
        }

        class ViewHolder(parent: ViewGroup) :
            RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_simple_text_water, parent, false
                )
            ) {
            val mText: TextView = itemView.findViewById(R.id.tv_list_item)
        }
    }

}


