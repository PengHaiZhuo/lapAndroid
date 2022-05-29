package com.phz.dev.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phz.common.appContext
import com.phz.dev.app.Constants
import com.phz.dev.data.room.bean.Practice
import com.phz.dev.data.room.dao.PracticeDao
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration


/**
 * @author phz
 * @description
 */
@Database(entities = [Practice::class], version = 2)
abstract class AppDataBase :RoomDatabase(){
abstract fun practiceDao():PracticeDao

    companion object{
        @Volatile private var mInstance: AppDataBase? = null
        fun getInstance(): AppDataBase {
            if (mInstance==null){
                mInstance=Room.databaseBuilder(
                    appContext,
                    AppDataBase::class.java, Constants.DATA_BASE_NAME
                )
//                    .fallbackToDestructiveMigration()//更新版本清空数据库
                    .addMigrations(MIGRATION_1_2)
                    .build()
                mInstance?.populateInitialData()
            }
            return mInstance!!
        }
    }

    /**
     * 将数据插入数据库
     */
    fun populateInitialData(){
        if (practiceDao().count()==0){
            runInTransaction{
                var bean=Practice()
                for(item in Practice.PRACTICES){
                    bean.name=item
                    practiceDao().insert(bean)
                }
            }
        }
    }

}
/**
 * 版本1到2迁移处理
 */
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DELETE FROM ${Practice.TABLE_NAME} WHERE name ='Paging3'")
    }
}
