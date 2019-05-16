package com.jh.autoupdate;

import android.content.Context;

public class AutoUpdateOption {

    //下载地址
    private String downLoadUrl;
    //安装地址
    private String installFilePath;
    //上下文
    private Context context;
    //app名称
    private String apkName;
    //当前应用程序最大sdk支持
    private int maxSdkCode;
    //背景(默认在后台显示)
    private AutoDownloadShowType autoDownloadShowType = AutoDownloadShowType.BACKGROUND;
    //通知的图标
    private int smallIcon;




    public AutoUpdateOption() {
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getInstallFilePath() {
        return installFilePath;
    }

    public void setInstallFilePath(String installFilePath) {
        this.installFilePath = installFilePath;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }


    public int getMaxSdkCode() {
        return maxSdkCode;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    public void setMaxSdkCode(int maxSdkCode) {
        this.maxSdkCode = maxSdkCode;
    }


    public AutoDownloadShowType getAutoDownloadShowType() {
        return autoDownloadShowType;
    }

    public void setAutoDownloadShowType(AutoDownloadShowType autoDownloadShowType) {
        this.autoDownloadShowType = autoDownloadShowType;
    }

    public static class Builder {

        private AutoUpdateOption autoUpdateOption = null;

        public Builder() {
            autoUpdateOption = new AutoUpdateOption();
        }

        public Builder with(Context context) {
            autoUpdateOption.setContext(context);
            return this;
        }

        public Builder downLoadUrl(String downLoadUrl) {
            autoUpdateOption.setDownLoadUrl(downLoadUrl);
            return this;
        }

        public Builder installFilePath(String installFilePath) {
            autoUpdateOption.setInstallFilePath(installFilePath);
            return this;
        }

        public Builder apkName(String apkName) {
            autoUpdateOption.setApkName(apkName);
            return this;
        }

        public Builder maxSdkCode(int maxSdkCode){
            autoUpdateOption.setMaxSdkCode(maxSdkCode);
            return this;
        }

        public Builder smallIcon(int smallIcon){
            autoUpdateOption.setSmallIcon(smallIcon);
            return this;
        }

        public Builder showType(AutoDownloadShowType autoDownloadShowType){
            autoUpdateOption.setAutoDownloadShowType(autoDownloadShowType);
            return this;
        }

        public AutoUpdateOption build() {
            return autoUpdateOption;
        }

    }

    public enum AutoDownloadShowType {
        BACKGROUND,
        FOREGROUND
    }

}
