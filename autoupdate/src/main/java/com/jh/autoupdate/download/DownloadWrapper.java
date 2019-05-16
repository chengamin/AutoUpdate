package com.jh.autoupdate.download;

import com.jh.autoupdate.AutoUpdateOption;

public class DownloadWrapper implements IDownloadWrapper {

    private AutoUpdateOption autoUpdateOption;

    public DownloadWrapper(AutoUpdateOption autoUpdateOption) {
        this.autoUpdateOption = autoUpdateOption;
    }

    @Override
    public void startDownload(OnDownloadResultListener onDownloadResultListener) {
        DownLoadUtils.DownLoadApk(autoUpdateOption,onDownloadResultListener);
    }


}
