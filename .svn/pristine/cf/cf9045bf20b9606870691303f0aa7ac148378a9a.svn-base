OCRLib 识别服务使用文档
===================

信大捷安
----


编写： 崔华伟 
日期： 2016-9-30
版本： version 1.0
使用环境：Android4.0以上版本


----------


需要在应用中集成OCRLib.apk识别功能应用，可以按照本说明来实现需要的功能。使用过程中，如果有问题请联系：  cuihauwei@xdja.com

#### <i class="icon-file"></i> OCRLib.apk集成
-------
认证文件安装
认证接口调用
> - 车牌识别调用
> - 身份证识别调用
> - 驾驶证识别调用
> - 护照识别调用


----------


#### <i class="icon-file"></i> 识别文件的安装

调用识别功能，需要首先在手机上安装OCRLib.apk文件，安装以后就可以调用这些功能了。您可以选择手动安装或者是嵌入到程序里面，由程序自动安装。

下面介绍通过程序自动安装的情况。
> - 在需要识别功能的程序里面新建一个asset文件夹，把需要安装的OCRLib.apk拷贝到这个文件下面。
> - 在程序启动的时候判断程序是否已经安装，如果没有安装，那么开始启动安装程序：
```
// check ocrlib.apk is installed.
private boolean checkPackageInstalled() {
    boolean flag = PackageUtil.isInstalled(this, "com.xdja.zdsb");
    return flag;
}

// install APK
public boolean installApk(){
    if (checkPackageInstalled（）) {
        return true;
    }
    
    // copy file from asset fold.
    String filename = copyFileFromAsset();

    try {
        Runtime.getRuntime().exec("chmod 644 " + filename);
    } catch (IOException e) {
	e.printStackTrace();
	return false;
    }

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.fromFile(new File(filename)),
		"application/vnd.android.package-archive");
    
    context.startActivity(intent);
    return true;
}

private String copyFileFromAsset() {

    String filename ="/data/data/{%packagename%}/files/ORClib.apk"

    InputStream in = null;
    FileOutputStream out = null;

    try {
        in = (InputStream)this.getResources().getAssets().open(Security);
        out = this.openFileOutput(Security, this.MODE_PRIVATE);
        byte[] buffer = new byte[8192];
        int count = 0;

        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        in.close();
        out.close();
    }

    return filename;
}


```


----------


#### <i class="icon-file"></i>认证接口调用

调用识别功能，是使用Intent filter 来直接调用识别库中的Activity， 调用后启动Activity 并打开相机开始扫描，扫描后会弹出一个对话框，提示识别结果，如果结果没有错误，点击确认。如果识别错误，可以点击重新扫描来继续完成扫面。

识别正确后调用方会得到一个Result 通过重载onActivityResult函数来获得识别结果数据。


----------


> - 车牌识别调用

车牌识别调用的 action filter： 
String CPSB = "com.xdja.zdsb.cpsb.action";

调用方法如下：
```
private static final int CAR_PLATE_REQUEST = 1;
public void recoginerCarPlate() {

    Intent intent = new Intent();
    Toast.makeText(mContext,
         "请将车牌放置在边框内进行扫描效果更佳！",
         Toast.LENGTH_SHORT)
         .show();
    intent.setAction(CPSB);
    startActivityForResult(intent, CAR_PLATE_REQUEST);
}
```
识别完成后，会返回识别结果。 通过onActivityResult读取识别数据。
可以直接通过data.getStringExtra("number"); 获得车牌号码
可以通过data.getStringExtra("data"); 获得车牌颜色，号码等信息。

```
@Override
protected void onActivityResult(int requestCode, 
            int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case CAR_PLATE_REQUEST:
                String number = null, result  = null;
                try{
                    number = data.getStringExtra("number");
                }catch (Exception e) {
                    e.printStackTrace();
                }
                result = data.getStringExtra("data");
                break;
            default:
                ;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


```


----------


> - 身份证识别调用

身份证识别调用的 action filter： 
String SFZSB = "com.xdja.zdsb.sfzsb.action";

调用方法如下：
```
private static final int ID_CARD_REQUEST = 2;
public void recoginerCarPlate() {

    Intent intent = new Intent();
    Toast.makeText(mContext,
         "请将身份证放置在边框内进行扫描",
         Toast.LENGTH_SHORT)
         .show();
    intent.setAction(SFZSB);
    startActivityForResult(intent, ID_CARD_REQUEST);
}
```

识别完成后，会返回识别结果。 通过onActivityResult读取识别数据。
可以通过data.getStringExtra("data"); 获得姓名，身份证号码等信息。
代码如下：
```
@Override
protected void onActivityResult(int requestCode, 
            int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case ID_CARD_REQUEST:
                String result  = null;
                result = data.getStringExtra("data");
                break;
            default:
               ;
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```


----------


> - 驾驶证识别调用

驾驶证识别调用的 action filter： 
String JSZSB = "com.xdja.zdsb.jszsb.action";

调用方法如下：
```
private static final int DRIVER_LICENSE_REQUEST = 3;
public void recoginerCarPlate() {

    Intent intent = new Intent();
    Toast.makeText(mContext,
         "请将身份证放置在边框内进行扫描",
         Toast.LENGTH_SHORT)
         .show();
    intent.setAction(JSZSB );
    startActivityForResult(intent, DRIVER_LICENSE_REQUEST);
}
```
识别完成后，会返回识别结果。 通过onActivityResult读取识别数据。
可以通过data.getStringExtra("data"); 获得姓名，号码等信息。
代码如下：

```

@Override
protected void onActivityResult(int requestCode, 
            int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case DRIVER_LICENSE_REQUEST:
                String result  = null;
                result = data.getStringExtra("data");
                break;
            default:
               ;
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```


----------


> - 护照识别调用

护照识别调用的 action filter： 
String PASSWORTSB = "com.xdja.zdsb.passport.action";

调用方法如下：
```
private static final int PASSPORT_REQUEST = 4;
public void recoginerCarPlate() {

    Intent intent = new Intent();
    Toast.makeText(mContext,
         "请将身份证放置在边框内进行扫描",
         Toast.LENGTH_SHORT)
         .show();
    intent.setAction(PASSWORTSB);
    startActivityForResult(intent, PASSPORT_REQUEST);
}
```

识别完成后，会返回识别结果。 通过onActivityResult读取识别数据。
可以通过data.getStringExtra("data"); 获得姓名，号码等信息。
代码如下：

```

@Override
protected void onActivityResult(int requestCode, 
            int resultCode, Intent data) {

    if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case PASSPORT_REQUEST:
                String result  = null;
                result = data.getStringExtra("data");
                break;
            default:
               ;
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```



