# AutoUpdate
添加方式:
1. 在project下的build.gradle中添加
    allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
2. 在module下的build.gradle中添加
  implementation 'com.github.chengamin:autoupdate:3.0'
  implementation "com.squareup.okhttp3:okhttp:3.14.1"
  以及
  compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
3. 结束了,开始用吧,哈哈!    
使用方式:
1.  autoUpdateOption = new AutoUpdateOption.Builder()
                .apkName("fg.apk")
                .downLoadUrl("http://218.26.67.174:9909/App/fg.apk")
                .with(this)
                .installFilePath(getExternalFilesDir(null).getPath())
                .maxSdkCode(Build.VERSION.SDK_INT)
                .smallIcon(R.mipmap.ic_launcher)
                .showType(AutoUpdateOption.AutoDownloadShowType.BACKGROUND)
                .build();
        autoUpdate = new AutoUpdate(autoUpdateOption);
    autoUpdate.download(new OnDownloadResultListener(){
         @Override
            public void onStartDownload() {
                //开始回调
            }

            @Override
            public void onProgressDownload(int progress) {
                // 回调中
            }

            @Override
            public void onCompleteDownload(Intent intent) {
                //下载完成
            }

            @Override
            public void onFailDownload(String errorMsg) {
                //下载出错
            }
    });
2. 具体弹框还是需要在通知栏显示,由你自己进行控制    
