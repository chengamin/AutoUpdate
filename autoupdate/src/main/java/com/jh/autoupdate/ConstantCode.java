package com.jh.autoupdate;

import android.Manifest;

/**
 * 自动更新中所有的常量
 */
public class ConstantCode {

    //权限申请编码
    public static final int REQUEST_PERMISSION_CODE = 0X001;
    //自动安装8.0跳转编码
    public static final int REQUEST_O_INSTALL_CODE = 0X002;


    public static final String REQUEST_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

}
