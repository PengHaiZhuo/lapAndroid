package com.phz.dev.data.room.bean

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phz.dev.data.room.bean.Practice.Companion.TABLE_NAME

/**
 * @author phz
 * @description 练习
 */
@Entity(tableName = TABLE_NAME)
data class Practice(
    @ColumnInfo(name =COLUMN_NAME)
    var name: String=""
) {
    companion object {
        const val TABLE_NAME="practices"/*表名*/
        val PRACTICES = arrayListOf(
            "Screen Record",
            "ViewPager Transformer",
            "Lottie",
            "Scan",
            "DrawerLayout",
            "PopupWindow",
            "ViewStub", "Dialog",
            "ExpandableListView",
            "DropDownList",
            "Rv Payload Use",
            "BaiduMapView",
            "Path",
            "Storage Access Framework",
            "Paging3",
            "ARP LIST"
        )

        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_NAME = "name"

        fun fromContentValues(values:ContentValues?):Practice{
            var practice=Practice()
            values?.let {
                if (values.containsKey(COLUMN_ID)){
                    practice.id=values.getAsLong(COLUMN_ID)
                }
                if (values.containsKey(COLUMN_NAME)){
                    practice.name=values.getAsString(COLUMN_NAME)
                }
            }
            return practice
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    var id: Long = 0L
}