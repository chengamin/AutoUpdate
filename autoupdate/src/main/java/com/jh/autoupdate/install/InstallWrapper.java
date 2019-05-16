package com.jh.autoupdate.install;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.jh.autoupdate.AutoUpdateOption;
import com.jh.autoupdate.ConstantCode;

import java.io.File;

public class InstallWrapper implements IInstallWrapper {

    private AutoUpdateOption autoUpdateOption;

    public InstallWrapper(AutoUpdateOption autoUpdateOption) {
        this.autoUpdateOption = autoUpdateOption;
    }
    @SuppressLint("NewApi")
    @Override
    public void install(Intent startIntent) {
        //检查当前版本是否大于8.0,如果大于等于8.0,需要先去设置临时权限
       /* if (autoUpdateOption.getMaxSdkCode() >= Build.VERSION_CODES.O) {
            //targetSdkVersion 为26以下的,canRequestPackageInstalls()方法会一直返回false 26以上才会返回正确
            boolean flag = autoUpdateOption.getContext().getPackageManager().canRequestPackageInstalls();
            //如果已经通过,直接安装,如果不通过,先去设置
            if (flag) {
                autoUpdateOption.getContext().startActivity(startIntent);
            } else {
                Uri packageUrl = Uri.parse("package:" + autoUpdateOption.getContext().getPackageManager());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUrl);
                ((Activity) autoUpdateOption.getContext()).startActivityForResult(intent, ConstantCode.REQUEST_O_INSTALL_CODE);
            }
        }else{
            autoUpdateOption.getContext().startActivity(startIntent);
        }*/
        autoUpdateOption.getContext().startActivity(startIntent);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (requestCode==ConstantCode.REQUEST_O_INSTALL_CODE){
           if (resultCode==Activity.RESULT_OK){
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               File apkFile = new File(autoUpdateOption.getInstallFilePath()+"/"+autoUpdateOption.getApkName());
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                   String authority = autoUpdateOption.getContext().getPackageName() + ".fileProvider";
                   Uri fileUri = FileProvider.getUriForFile(autoUpdateOption.getContext(), authority, apkFile);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // 表示文件类型
                   intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                   intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
               } else {
                   Uri uri = Uri.fromFile(apkFile);
                   intent.setDataAndType(uri, "application/vnd.android.package-archive");
               }
               autoUpdateOption.getContext().startActivity(intent);
           }
       }

    }
}
