package com.phz.common.util;

import android.app.AppOpsManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.phz.common.BaseApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author phz
 * @introduction 通知栏工具类
 * @notice 在Android10或更高版本中，如果要点击通知启动与时效性通知关联的全屏 Activity，则必须在清单文件中请求USE_FULL_SCREEN_INTENT权限
 * @see [https://developer.android.google.cn/training/notify-user/build-notification]
 */
class NotificationUtils {
    private static NotificationUtils mInstance;
    /**
     * 通知栏管理器
     */
    private static NotificationManager manager;
    /**
     * 通知栏频道设置
     */
    private static NotificationChannel notificationChannel;
    /**
     * 通知构造器
     * 在使用最新通知 API 功能的同时仍然支持旧版设备，不需要您编写代码检查Api级别。
     * 需要注意调用某个方法的时候不能保作所有设备都有这个功能，可能出现空操作。比如addAction只会在Api 16以上生效。
     */
    private static NotificationCompat.Builder builder;

    public NotificationUtils(Application application) {
        //获取NotificationManager对象
        manager = (NotificationManager) application.getSystemService(Service.NOTIFICATION_SERVICE);
    }

    /**
     * 单一实例
     */
    public static NotificationUtils getInstance() {
        if (mInstance == null) {
            mInstance = new NotificationUtils(BaseApplication.instance);
        }
        return mInstance;
    }

    /**
     * @param title         通知标题
     * @param msg           通知消息
     * @param channelId     渠道id
     * @param channelName   渠道名
     * @param requestId     区分不同的通知，这个id用于更新(也可以叫覆盖)和移除某个通知
     *                      一个独特的Integer，比如使用系统时间{int requestId = (int) System.currentTimeMillis();}
     * @param smallIcon     小图标
     * @param largeIcon     大图标
     * @param pendingIntent 指定意图和执行目标动作的Intent，往往用于通知点击跳转
     */

    public static void showNotification(String title, String msg, String channelId, String channelName, int requestId, int smallIcon, Bitmap largeIcon, PendingIntent pendingIntent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //sdk26以上（8.0）需要指定通道
            notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            //设置可以绕过请勿打扰模式
            notificationChannel.setBypassDnd(true);
            //可否绕过请勿打扰模式
            notificationChannel.canBypassDnd();
            //锁屏显示通知
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            //是否会闪光
            notificationChannel.shouldShowLights();
            //闪光
            notificationChannel.enableLights(true);
            //指定闪光时的灯光颜色，低版本在builder上通过setLights方法设置{builder.setLights(Color.GREEN, 1000, 1000);}
            notificationChannel.setLightColor(Color.RED);
            //此通道桌面launcher消息角标不显示
            notificationChannel.setShowBadge(false);
            //是否允许震动
            notificationChannel.enableVibration(true);
            //震动模式，第一次100ms，第二次100ms，第三次200ms，低版本在Notification.Builder上设置{builder.setVibrate(new long[]{200, 1000});}
            notificationChannel.setVibrationPattern(new long[]{100, 100, 200});
            //获取系统通知响铃声音的配置
            notificationChannel.getAudioAttributes();
            //获取通知渠道组
            notificationChannel.getGroup();
            /**
             * 先建一个渠道
             */
            manager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(BaseApplication.instance, channelId);
            //设置标题
            builder.setContentTitle(title);
            //设置文本
            builder.setContentText(msg);
            builder.setSmallIcon(smallIcon, 10000);
            builder.setLargeIcon(largeIcon);
            builder.setContentIntent(pendingIntent);
            //点击通知后后自动取消
            builder.setAutoCancel(true);
        } else {
            //channelId传个null即可，旧版本会忽略
            builder = new NotificationCompat.Builder(BaseApplication.instance);
            builder.setContentIntent(pendingIntent);
            //设置标题
            builder.setContentTitle(title);
            //设置文本
            builder.setContentText(msg);
            builder.setSmallIcon(smallIcon, 10000);
            builder.setLargeIcon(largeIcon);
            //查看后自动取消
            builder.setAutoCancel(true);
            //消息提示模式
            builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            //设置震动规律，（第一个参数: 振动前等待的时间，第二个参数： 第一次振动的时长、以此类推  ）
            builder.setVibrate(new long[]{200, 1000});
            //设置灯
            builder.setLights(Color.GREEN, 1000, 1000);
            //设置通知优先级,默认是PRIORITY_DEFAULT（Android 7.1及以下需要设置，8.0及以上在创建渠道的时候有指定渠道重要性）
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            //设置发送到辅助功能服务的“ ticker”文本；在LOLLIPOP之前，第一次到达通知时在状态栏显示的文本
            /*builder.setTicker("你收到一条新通知");*/
            //什么时候发出通知
            /*builder.setWhen(SystemClock.currentThreadTimeMillis());*/
            //添加按钮以及按钮点击意图（Api级别16 JELLY_BEAN开始支持此方法）
            /*builder.addAction(R.drawable.ic_xx,"",xxIntent);*/
        }
        //发送通知
        manager.notify(requestId, builder.build());
    }

    /**
     * 通道适配
     *
     * @notice 调用时机尽可能早，比如在各渠道sdk初始化成功之前就创建其对应的通道，才能正常接收消息
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public static void initNotificationChannel(String id, String name) {
        //8.0通道适配
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) BaseApplication.instance.getSystemService(Service.NOTIFICATION_SERVICE);
        //创建好通道后，通过代码createNotificationChannel仅仅只能更新其name/description以及对importance进行降级，其余配置均无法更新。
        //反复调用createNotificationChannel是安全的，因为创建现有通知渠道不会执行任何操作。
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * 应用是否允许通知
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
