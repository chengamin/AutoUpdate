package com.jh.autoupdate;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.jh.autoupdate.download.OnDownloadResultListener;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private AutoUpdate autoUpdate = null;
    private AutoUpdateOption autoUpdateOption = null;


    //定义notify的id，避免与其它的notification的处理冲突
    private static final int NOTIFY_ID = 0;
    private static final String CHANNEL = "update";

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotification();

        autoUpdateOption = new AutoUpdateOption.Builder()
                .apkName("simple.apk")
                .downLoadUrl("https://github.com/crazycodeboy/TakePhoto/raw/master/simple/simple.apk")
                .with(this)
                .installFilePath(getExternalFilesDir(null).getPath())
                .maxSdkCode(Build.VERSION.SDK_INT)
                .smallIcon(R.mipmap.ic_launcher)
                .showType(AutoUpdateOption.AutoDownloadShowType.BACKGROUND)
                .build();
        autoUpdate = new AutoUpdate(autoUpdateOption);
        autoUpdate.download(new OnDownloadResultListener() {
            @Override
            public void onStartDownload() {
                mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
            }

            @Override
            public void onProgressDownload(int progress) {
                mBuilder.setContentTitle("正在下载：新版本...")
                        .setContentText(String.format(Locale.CHINESE, "%d%%", progress))
                        .setProgress(100, progress, false)
                        .setWhen(System.currentTimeMillis());
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(NOTIFY_ID, notification);
            }

            @Override
            public void onCompleteDownload(Intent intent) {

                PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this
                        ,ConstantCode.REQUEST_O_INSTALL_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pIntent)
                        .setContentTitle(getPackageName())
                        .setContentText("下载完成，点击安装")
                        .setProgress(0,0,false)
                        .setDefaults(Notification.DEFAULT_ALL);
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(NOTIFY_ID,notification);
            }

            @Override
            public void onFailDownload(String errorMsg) {

            }
        });


    }

    private void initNotification() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("1", "Channel1", NotificationManager.IMPORTANCE_MIN);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        mBuilder = new NotificationCompat.Builder(this,"1");
        mBuilder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setAutoCancel(true)
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                .setWhen(System.currentTimeMillis());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        autoUpdate.onRequestPermissionResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        autoUpdate.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
