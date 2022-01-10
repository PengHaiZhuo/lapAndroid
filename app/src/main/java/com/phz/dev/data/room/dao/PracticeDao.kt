package com.phz.dev.data.room.dao

import android.database.Cursor
import androidx.room.*
import com.phz.dev.data.room.bean.Practice

/**
 * @author phz
 * @description
 */
@Dao
interface PracticeDao {
    @Query("SELECT COUNT(*) FROM ${Practice.TABLE_NAME}")
    fun count(): Int /*获取数据量*/

    @Insert
    fun insert(practice: Practice): Long /*插入*/

    @Insert
    fun insertAll(practices: Array<Practice>):Array<Long> /*插入一批数据*/

    @Query("SELECT * FROM ${Practice.TABLE_NAME}")
    fun selectAll(): Cursor /*查所有*/

    @Query("SELECT * FROM ${Practice.TABLE_NAME} WHERE ${Practice.COLUMN_ID} = :id")
    fun selectById(id: Long): Cursor /*根据id查*/

    @Update
    fun update(practice: Practice): Int /*更新*/

    @Query("DELETE FROM ${Practice.TABLE_NAME}")
    fun deleteAll(): Int /*删除所有数据*/

    @Query("DELETE FROM ${Practice.TABLE_NAME} WHERE ${Practice.COLUMN_ID} = :id")
    fun deleteById(id: Long): Int /*根据id删除*/

    @Query("SELECT * FROM ${Practice.TABLE_NAME}")
    fun getList(): List<Practice>  /*获取所有数据*/

    /**
     * 用这种方式不是很友好，举个例子
     * var tempItemList = ArrayList<Practice>()
     * //给tempItemList添加一些数据...
     * dao.insertModuleItems(*tempItemList.toTypedArray()) /*这里用了一个'*',感觉多此一举了*/
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(vararg modules: Practice)  /*插入一批数据，冲突策略为替换*/

    @Transaction
    @Query("SELECT * FROM ${Practice.TABLE_NAME} WHERE name LIKE :name")
    suspend fun getPracticeLike(name: String): List<Practice>  /*模糊查找满足条件数据*/
}