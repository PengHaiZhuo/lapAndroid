package com.phz.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.phz.common.BaseApplication;

import java.io.File;

/**
 * @author phz
 * @introduction 工具类，存储文件
 */
public class StorageUtil {
    private StorageUtil() {
    }

    /**
     * 判断外存储是否可写
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static File getAppDir() {
        File rootDir;
        //从 Android 4.4 到 Android 10，可以通过 Environment.getExternalStorageDirectory() 以 File Api 的方式读写。
        //6.0以上需要申请读写权限，Android 10需要在配置清单声明兼容（requestLegacyExternalStorage = true）。
        //Android 10开始可以做分区适配，如果不做适配在拥有读写权限的情况下，和之前一样。
        //通过Context访问自己的私有目录，不需要读写权限，不管在哪个版本或者是外部存储还是内部存储。
        //通过Storage Access Framework的Api不需要权限，可以访问其他应用创建的文件。
        if (isExternalStorageWritable() && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            //返回外部存储下应用名字文件夹
            rootDir = new File(Environment.getExternalStorageDirectory(), BaseApplication.appName);
        } else {
            //返回外部存储私有目录文件夹
            rootDir = BaseApplication.instance.getFilesDir();
        }
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return rootDir;
    }

    /**
     * 获取当前app文件存储目录
     *
     * @return
     */
    public static File getFileDir() {
        File fileDir = new File(getAppDir(), "file");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }


    /**
     * 获取当前app图片文件存储目录
     *
     * @return
     */
    public static File getImageDir() {
        File imageDir = new File(getAppDir(), "image");
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    /**
     * 获取当前app缓存文件存储目录
     *
     * @return
     */
    public static File getCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            File cacheDir = new File(getAppDir(), "cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            return cacheDir;
        } else {
            return BaseApplication.instance.getCacheDir();
        }
    }

    /**
     * 获取当前app的pdf文件存储目录
     *
     * @return
     */
    public static File getPdfDir() {
        File cacheDir = new File(getAppDir(), "pdf");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }


    /**
     * 获取当前app音频文件存储目录
     *
     * @return
     */
    public static File getAudioDir() {
        File audioDir = new File(getAppDir(), "audio");
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        return audioDir;
    }

    /**
     * @param context
     * @return "/storage/emulated/0/Android/data/com.xxx.xxx/cache"目录
     */
    public static String getExternalCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 创建一个文件夹, 存在则返回, 不存在则新建
     *
     * @param parentDirectory 父目录路径
     * @param directory       目录名
     * @return 文件，null代表失败
     */
    public static File getDirectory(String parentDirectory, String directory) {
        if (TextUtils.isEmpty(parentDirectory) || TextUtils.isEmpty(directory)) {
            return null;
        }
        File file = new File(parentDirectory, directory);
        boolean flag;
        if (!file.exists()) {
            flag = file.mkdir();
        } else {
            flag = true;
        }
        return flag ? file : null;
    }

    /**
     * 获取文件Uri,适配7.0
     * @param context
     * @param mFile
     * @return
     */
    public static Uri getUriFromFile(Context context, File mFile){
        Uri fileUri;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            //第二个参数与配置文件中provider的authorities字段相同
            //FileProvider 只能返回在其<paths>元数据元素中定义的文件路径的content Uri,详见res/xml/file_paths.xml
            fileUri= FileProvider.getUriForFile(context,context.getPackageName(),mFile);
        }else {
            fileUri=Uri.fromFile(mFile);
        }
       /**
        * 建议直接使用Intent的addFlags添加权限，Intent在接收堆栈Activity处于活动状态时，授权有效，从堆栈移出后，权限将自动删除
        //授予URI临时权限
        context.grantUriPermission(context.getPackageName(),fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //使用完后移除临时授权
        context.revokeUriPermission(fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        */
        return fileUri;
    }

}
