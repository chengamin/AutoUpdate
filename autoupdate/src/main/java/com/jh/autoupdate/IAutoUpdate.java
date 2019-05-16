package com.jh.autoupdate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jh.autoupdate.download.OnDownloadResultListener;

public interface IAutoUpdate {

    //开始下载
    void download(OnDownloadResultListener onDownloadResultListener);

    //开始安装
    void install(Intent intent);

    //权限申请调用
    void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    //页面回调
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
