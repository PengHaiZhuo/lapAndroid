package com.phz.dev.feature.practice.list.listview

import android.os.Bundle
import com.google.android.material.checkbox.MaterialCheckBox
import com.phz.common.ext.showDialogMessage
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityStudentListBinding
import com.phz.dev.feature.practice.list.listview.adapter.ClassRoomAdapter
import com.phz.dev.feature.practice.list.listview.adapter.OnItemClickListener
import com.phz.dev.feature.practice.list.listview.model.ClassRoomBean
import com.phz.dev.feature.practice.list.listview.model.StudentBean

/**
 * @author phz on 2021/9/26
 * @description 学生列表
 * 关于3层及以上的扩展列表，可以参考开源项目 BaseRecyclerViewAdapterHelper中的Expandable Item，或者通过嵌套多层ExpandableListView(这非常麻烦)
 * 关于饿了么2级联动列表，可以参考开源项目 Linkage-RecyclerView
 * 关于左右侧滑Item的列表，可以参考开源项目SwipeRecyclerView
 */
class StudentListActivity : BaseVmDbActivity<NoViewModel, ActivityStudentListBinding>() {

    var classList = arrayListOf(
        ClassRoomBean("3年1班", arrayListOf(StudentBean("张电"), StudentBean("李电"), StudentBean("王树林"))),
        ClassRoomBean("3年2班", arrayListOf(StudentBean("赵一"), StudentBean("钱二"), StudentBean("孙三"))),
        ClassRoomBean("3年3班", arrayListOf(StudentBean("周波"), StudentBean("吴迪"), StudentBean("郑强")))
    )

    private val mExAdapter by lazy {
        ClassRoomAdapter(this@StudentListActivity, classList, object : OnItemClickListener {

            override fun checkedChange(groupPosition: Int, childPosition: Int, isChecked: Boolean) {
                val msg =
                    "${classList[groupPosition].name}${classList[groupPosition].student[childPosition].name}同学状态改变"
                showDialogMessage(msg,"提示")
            }

        })
    }

    override fun initData() {
        mViewDataBinding.exList.setAdapter(mExAdapter)
        //默认展开第一个分组
        mViewDataBinding.exList.expandGroup(0,true)
        mViewDataBinding.exList.setOnChildClickListener { _, v, _, _, _ ->
            val cbChild: MaterialCheckBox = v!!.findViewById(R.id.cb_student)
            val isChecked = cbChild.isChecked
            cbChild.isChecked = !isChecked
            true
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "ExpandableListView"
    }

}