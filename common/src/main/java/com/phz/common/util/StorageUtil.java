package com.phz.common.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.phz.common.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

//    public static void writeSelfFile_In_ExternalPublicCategory(String fileName,String fileType){
//        ContentValues contentValues=new ContentValues();
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
//            //相对路径
//            contentValues.put(MediaStore.Files.FileColumns.RELATIVE_PATH,BaseApplication.appName+"file");
//        }
//        //名称
//        contentValues.put(MediaStore.Files.FileColumns.DISPLAY_NAME,fileName);
//        //文件类型
//        contentValues.put(MediaStore.Files.FileColumns.MEDIA_TYPE);
//    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @NonNull
    private Uri savePDFFile(@NonNull final Context context, @NonNull InputStream in,
                            @NonNull final String mimeType,
                            @NonNull final String displayName, @Nullable final String subFolder) throws IOException {
        String relativeLocation = Environment.DIRECTORY_DOCUMENTS;

        if (!TextUtils.isEmpty(subFolder)) {
            relativeLocation += File.separator + subFolder;
        }

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);
        contentValues.put(MediaStore.Video.Media.TITLE, "SomeName");
        contentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
        final ContentResolver resolver = context.getContentResolver();
        OutputStream stream = null;
        Uri uri = null;

        try {
            final Uri contentUri = MediaStore.Files.getContentUri("external");
            uri = resolver.insert(contentUri, contentValues);
            ParcelFileDescriptor pfd;
            try {
                assert uri != null;
                pfd = context.getContentResolver().openFileDescriptor(uri, "w");
                assert pfd != null;
                FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());

                byte[] buf = new byte[4 * 1024];
                int len;
                while ((len = in.read(buf)) > 0) {

                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                pfd.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            contentValues.clear();
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
            context.getContentResolver().update(uri, contentValues, null, null);
            stream = resolver.openOutputStream(uri);
            if (stream == null) {
                throw new IOException("Failed to get output stream.");
            }
            return uri;
        } catch (IOException e) {
            // Don't leave an orphan entry in the MediaStore
            resolver.delete(uri, null, null);
            throw e;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
