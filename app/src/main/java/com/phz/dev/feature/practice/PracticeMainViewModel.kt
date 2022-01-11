package com.phz.dev.feature.practice

import android.database.Cursor
import androidx.lifecycle.ViewModel
import com.phz.dev.data.room.bean.Practice
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainViewModel: ViewModel() {
    var practiceList=MutableStateFlow<List<String>>(emptyList())

    fun formatCursorData(cursor:Cursor?){
        val list= arrayListOf<String>()
        cursor?.let {
            if(cursor.moveToFirst()){
                val first=cursor.getString(cursor.getColumnIndexOrThrow(Practice.COLUMN_NAME))
                list.add(first)//添加第一条
                while (cursor.moveToNext()){
                    //有下一条
                    val now=cursor.getString(cursor.getColumnIndexOrThrow(Practice.COLUMN_NAME))
                    list.add(now)//添加
                }
            }
        }
        practiceList.value=list
    }
}