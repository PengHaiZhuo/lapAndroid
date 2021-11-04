package com.phz.dev.feature.practice.list.listview

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.showLoadingExt
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityDropDwonTiBinding
import com.phz.dev.feature.practice.list.listview.adapter.ProjectAdapter
import com.phz.dev.feature.practice.list.listview.adapter.TaskAdapter
import com.phz.dev.feature.practice.list.listview.model.ProjectBean
import com.phz.dev.feature.practice.list.listview.model.TaskBean
import kotlinx.coroutines.delay

/**
 * @author phz on 2021/9/28
 * @description
 */
class DropDownMenuTlActivity :
    BaseVmDbActivity<DropDownMenuTlViewModel, ActivityDropDwonTiBinding>() {

    companion object {
        const val pageName = "DropDownSample"
        val projects = arrayListOf(
            ProjectBean(
                "湘江大桥", arrayListOf(
                    TaskBean("调索安装", "2021-01-01", 0, 0.0f),
                    TaskBean("猫道架设", "2021-01-01", 0, 0.0f),
                    TaskBean("索鞍安装", "2021-01-01", 0, 0.0f),
                    TaskBean("索股架设", "2021-01-01", 0, 0.0f),
                    TaskBean("索夹安装", "2021-01-01", 0, 0.0f),
                )
            ),
            ProjectBean(
                "洞庭大桥", arrayListOf(
                    TaskBean("猫道架设", "2021-01-01", 0, 0.0f),
                    TaskBean("索鞍安装", "2021-01-01", 0, 0.0f),
                    TaskBean("索股架设", "2021-01-01", 0, 0.0f),
                    TaskBean("索夹安装", "2021-01-01", 0, 0.0f),
                )
            ),
            ProjectBean(
                "银盆岭大桥", arrayListOf(
                    TaskBean("索鞍安装", "2021-01-01", 0, 0.0f),
                    TaskBean("索股架设", "2021-01-01", 0, 0.0f),
                    TaskBean("索夹安装", "2021-01-01", 0, 0.0f),
                )
            )
        )
    }

    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var taskAdapter: TaskAdapter
    private var projectList = arrayListOf<ProjectBean>()
    private var taskList = arrayListOf<TaskBean>()

    override fun initData() {
        projectAdapter = ProjectAdapter(this@DropDownMenuTlActivity, projectList)
        taskAdapter = TaskAdapter(this@DropDownMenuTlActivity, taskList)
        mViewDataBinding.autoTvProject.apply {
            showSoftInputOnFocus = false//设置获取焦点不显示软键盘
            setAdapter(projectAdapter)
            setOnItemClickListener { _, _, position, _ ->
                mViewDataBinding.autoTvProject.setText(projectList[position].name)
                mViewModel.indexOne.value = position
            }
        }
        mViewDataBinding.autoTvTask.apply {
            showSoftInputOnFocus = false//设置获取焦点不显示软键盘
            setAdapter(taskAdapter)
            setOnItemClickListener { _, _, position, _ ->
                mViewDataBinding.autoTvTask.setText(taskList[position].name)
                mViewModel.indexTwo.value = position
            }
        }

        mViewModel.indexOne.observe(this){
            //项目索引改变，初始化任务
            taskList.clear()
            taskList.addAll(projectList[it].gxList)
            taskAdapter.notifyDataSetChanged()
            mViewDataBinding.autoTvTask.setText(taskList[0].name)
            mViewModel.indexTwo.value=0
        }

        //模拟网络请求
        lifecycleScope.launchWhenResumed {
            showLoadingExt()
            delay(2000)
            projectList.clear()
            projectList.addAll(projects)
            projectAdapter.notifyDataSetChanged()
            taskList.clear()
            taskList.addAll(projects[0].gxList)
            taskAdapter.notifyDataSetChanged()
            dismissLoadingExt()
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = pageName
    }
}