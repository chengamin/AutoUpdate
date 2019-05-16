package com.jh.autoupdate.download;

import android.content.Intent;

public interface OnDownloadResultListener {

    void onStartDownload();

    void onProgressDownload(int progress);

    void onCompleteDownload(Intent intent);

    void onFailDownload(String errorMsg);

}
