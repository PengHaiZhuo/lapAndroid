package com.phz.dev.feature.practice

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.phz.dev.data.room.AppDataBase
import com.phz.dev.data.room.bean.Practice

/**
 * @author phz
 * @description 基于 Room 数据库的ContentProvider
 */
class PracticeContentProvider:ContentProvider() {
    companion object{
        const val AUTH="com.phz.android.cp.provider"
        val URI: Uri =Uri.parse("content://${AUTH}/${Practice.TABLE_NAME}")
        const val CODE_DIR= 1
        const val CODE_ITEM=2
        val MATCHER=UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        MATCHER.addURI(AUTH, Practice.TABLE_NAME, CODE_DIR)
        MATCHER.addURI(AUTH, Practice.TABLE_NAME+"/*", CODE_ITEM)
    }

    override fun onCreate(): Boolean =true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code= MATCHER.match(uri)
        if (code== CODE_DIR||code== CODE_ITEM){
            val context = context ?: return null
            val dao=AppDataBase.getInstance().practiceDao()
            var cursor: Cursor? = if (code==CODE_DIR){
                dao.selectAll()
            }else{
                dao.selectById(ContentUris.parseId(uri))
            }
            cursor!!.setNotificationUri(context.contentResolver, uri)
            return cursor
        }else{
            throw IllegalArgumentException("Unknown URI:${uri}")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(
            uri
        )) {
            CODE_DIR -> "vnd.android.cursor.dir/" + AUTH + "." + Practice.TABLE_NAME
            CODE_ITEM -> "vnd.android.cursor.item/" + AUTH + "." + Practice.TABLE_NAME
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (MATCHER.match(
            uri
        )) {
            CODE_DIR -> {
                val context = context ?: return null
                val id: Long = AppDataBase.getInstance().practiceDao()
                    .insert(Practice.fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }
            CODE_ITEM -> throw java.lang.IllegalArgumentException(
                "Invalid URI, cannot insert with ID: $uri"
            )
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (MATCHER.match(
            uri
        )) {
            CODE_DIR -> throw java.lang.IllegalArgumentException(
                "Invalid URI, cannot update without ID$uri"
            )
            CODE_ITEM -> {
                val context = context ?: return 0
                val count: Int = AppDataBase.getInstance().practiceDao()
                    .deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                count
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }

    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return when (MATCHER.match(
            uri
        )) {
            CODE_DIR -> throw java.lang.IllegalArgumentException(
                "Invalid URI, cannot update without ID$uri"
            )
            CODE_ITEM -> {
                val context = context ?: return 0
                val practice: Practice = Practice.fromContentValues(values)
                practice.id = ContentUris.parseId(uri)
                val count: Int = AppDataBase.getInstance().practiceDao()
                    .update(practice)
                context.contentResolver.notifyChange(uri, null)
                count
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }

    }
}