package com.jh.autoupdate.download;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;


import com.jh.autoupdate.AutoUpdateOption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadUtils {

    private static String apkFilePath = null;
    private static OnDownloadResultListener onDownLoadListener = null;
    private static AutoUpdateOption autoUpdateOption = null;

    private static float rate = .0f;

    private static Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    onDownLoadListener.onStartDownload();
                    break;
                case 2:
                    int progress = (int) msg.obj;
                    onDownLoadListener.onProgressDownload(progress);
                    break;
                case 3:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    File apkFile = new File(autoUpdateOption.getInstallFilePath()+"/"+autoUpdateOption.getApkName());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        String authority =  autoUpdateOption.getContext().getApplicationContext().getPackageName().toString()+".fileProvider";
                        Uri fileUri = FileProvider.getUriForFile(autoUpdateOption.getContext().getApplicationContext(), authority, apkFile);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // 表示文件类型
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
                    } else {
                        Uri uri = Uri.fromFile(apkFile);
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    }
                    onDownLoadListener.onCompleteDownload(intent);
                    break;
                case 4:
                    String errorMsg = (String) msg.obj;
                    onDownLoadListener.onFailDownload(errorMsg);
                    break;
            }
            return true;
        }
    });



    public static void DownLoadApk(final AutoUpdateOption autoUpdateOption, final OnDownloadResultListener onDownLoadListener) {
        DownLoadUtils.onDownLoadListener = onDownLoadListener;
        DownLoadUtils.autoUpdateOption = autoUpdateOption;
        Request request = new Request.Builder().url(autoUpdateOption.getDownLoadUrl()).build();
        mHandler.sendEmptyMessage(1);
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = mHandler.obtainMessage();
                message.what =4;
                message.obj = e.getMessage();
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean flag = false;
                if (response.body() == null) {
                    Message message = mHandler.obtainMessage();
                    message.what =4;
                    message.obj = "下载错误";
                    mHandler.sendMessage(message);
                } else {
                    InputStream is = null;
                    byte[] buff = new byte[2048];
                    int len;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = createFile(autoUpdateOption.getInstallFilePath(), autoUpdateOption.getApkName());
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buff)) != -1) {
                            fos.write(buff, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            if (rate != progress) {
                                Message message = mHandler.obtainMessage();
                                message.what = 2;
                                message.obj = progress;
                                mHandler.sendMessage(message);
                                rate = progress;
                            }
                        }
                        fos.flush();
                        flag = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                            if (fos != null)
                                fos.close();
                            if (flag){
                                //解决更新速率太快的问题
                                mHandler.sendEmptyMessageDelayed(3,5000);
                            }else{
                                //解决更新速率太快的问题
                                mHandler.sendEmptyMessageDelayed(4,5000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //解决更新速率太快的问题
                            mHandler.sendEmptyMessageDelayed(4,5000);
                        }
                    }
                }
            }
        });
    }


    /**
     * 路径为根目录
     * 创建文件名称为 updateDemo.apk
     */
    private static File createFile(String createFile, String apkName) {
        File file = new File(createFile, apkName);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
