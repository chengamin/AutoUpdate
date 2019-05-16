package com.jh.autoupdate;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jh.autoupdate.download.DownloadWrapper;
import com.jh.autoupdate.download.OnDownloadResultListener;
import com.jh.autoupdate.install.InstallWrapper;
import com.jh.autoupdate.permission.PermissionWrapper;

public class AutoUpdate implements IAutoUpdate {

    //配置项
    private AutoUpdateOption autoUpdateOption = null;
    private PermissionWrapper permissionWrapper = null;
    private DownloadWrapper downloadWrapper = null;
    private InstallWrapper installWrapper = null;
    private OnDownloadResultListener onDownloadResultListener = null;

    /**
     * 必须拥有配置项才可以进行初始化
     *
     * @param autoUpdateOption
     */
    public AutoUpdate(AutoUpdateOption autoUpdateOption) {
        this.autoUpdateOption = autoUpdateOption;
        permissionWrapper = new PermissionWrapper(autoUpdateOption.getContext());
        downloadWrapper = new DownloadWrapper(autoUpdateOption);
        installWrapper = new InstallWrapper(autoUpdateOption);
    }

    /**
     * 开始下载
     */
    @Override
    public void download(OnDownloadResultListener onDownloadResultListener) {
        this.onDownloadResultListener = onDownloadResultListener;
        // 1. 检查权限(读写权限)
       if (permissionWrapper.startPermission()){
           // 2. 开始下载并且进行安装
           downloadWrapper.startDownload(onDownloadResultListener);
       }
    }

    @Override
    public void install(Intent intent) {
         installWrapper.install(intent);
    }


    /**
     * Activity中进行回调
     */
    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 1.权限回调
        if (requestCode==ConstantCode.REQUEST_PERMISSION_CODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                // 2. 开始下载并且进行安装
                downloadWrapper.startDownload(onDownloadResultListener);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        installWrapper.onActivityResult(requestCode, resultCode, data);
    }



}
