package com.phz.common.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

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
        //6.0以上需要申请读写权限，Android 10需要再清单声明兼容（requestLegacyExternalStorage = true）。Android 11起使用 MediaStore等。
        //值得注意的是，通过Context访问自己的私有目录，不需要读写权限。
        if (isExternalStorageWritable() && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            rootDir = new File(Environment.getExternalStorageDirectory(), BaseApplication.appName);
        } else {
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
}
