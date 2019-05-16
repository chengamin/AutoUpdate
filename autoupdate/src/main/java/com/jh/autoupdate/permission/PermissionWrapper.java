package com.jh.autoupdate.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.jh.autoupdate.ConstantCode;

public class PermissionWrapper implements IPermissionWrapper{

    private Context context = null;

    public PermissionWrapper(Context context) {
        this.context = context;
    }

    @Override
    public boolean  startPermission() {
        boolean flag = checkPermission();
        if (!flag){
            requestPermission();
        }
        return flag;
    }


    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, ConstantCode.REQUEST_PERMISSION)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity)context,new String[]{ConstantCode.REQUEST_PERMISSION},ConstantCode.REQUEST_PERMISSION_CODE);
    }




}
