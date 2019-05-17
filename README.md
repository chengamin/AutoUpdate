 
# AutoUpdate
## 添加方式:
### 1. 在project下的build.gradle中添加
```Gradle
    allprojects {
    	repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```    
### 2. 在module下的build.gradle中添加
```Gradle
  implementation 'com.github.chengamin:autoupdate:1.0'
  implementation "com.squareup.okhttp3:okhttp:3.14.1"
  以及
  compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```    
### 3. 结束了,开始用吧,哈哈!   

## 使用方式:  
```java
autoUpdateOption = new AutoUpdateOption.Builder()
        .apkName("fg.apk")
        .downLoadUrl("xxx.apk")
        .with(this)
        .installFilePath(getExternalFilesDir(null).getPath())
        .maxSdkCode(Build.VERSION.SDK_INT)
        .smallIcon(R.mipmap.ic_launcher)
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
```    
### 具体弹框还是需要在通知栏显示,由你自己进行控制   (参考app中的MainActivity中的通知进行使用,弹框太简单不做示范)

