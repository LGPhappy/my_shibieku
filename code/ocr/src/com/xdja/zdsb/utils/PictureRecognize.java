package com.xdja.zdsb.utils;

import com.google.gson.Gson;
import com.wintone.plateid.PlateCfgParameter;
import com.wintone.plateid.PlateRecognitionParameter;
import com.wintone.plateid.RecogService;
import com.xdja.zdsb.bean.CacheBean;
import com.xdja.zdsb.bean.CarBean;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Vibrator;
import android.text.TextUtils;
import wintone.idcard.android.RecogParameterMessage;
import wintone.idcard.android.ResultMessage;
import wintone.idcard.android.RecogService.recogBinder;

public class PictureRecognize {

    public static final int CAR_PLATE = 1;

    public static final int ID_CARD = 2;

    public static final int DRIVER_LICENSE = 3;

    public static final int PASSPORT = 4;

    public static final int MAX_WIDTH = 2048;

    public static final int MAX_HEIGHT = 1536;

    // 推荐使用的分辨率为1280*960，其次是1600*1200，最后是2048*1536。
    public static final int MAX_ID_WIDTH = 1280;

    public static final int MAX_ID_HEIGHT = 960;

    private static final String TAG = "PictureRecognize";

    RecognizerInterface recognizerInterface = null;

    private int picType;

    private String picPath;

    private String tempPic = FileUtils.PATH + "/temp_pic.jpg";

    Bitmap recogBitmap;

    private int width, height;

    private Context context;

    private RecogService.MyBinder recogCarPlateBinder;

    private recogBinder recogIDBinder; // idcard binder

    private IdServiceConnection recogIdConn;

    public PictureRecognize(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void doRecognize(String picPath, int type, RecognizerInterface recognizerInterface) {

        CacheBean.jsonbean = null;

        picType = type;

        this.recognizerInterface = recognizerInterface;

        this.picPath = picPath;

        new Thread(new Runnable() {

            @Override
            public void run() {

                getPicProperties();

                switch (picType) {
                case CAR_PLATE:
                    recognizeCarplate();
                    break;
                case ID_CARD:
                case DRIVER_LICENSE:
                case PASSPORT:
                    recognizeIdCard(picType);
                    break;
                default:
                    Zzlog.out(TAG, "unKnow picture type.");
                }
            }

        }).start();
        
        
    }
    
    



    protected void recognizeIdCard(int pictype) {
        Zzlog.out(TAG, "recognizeIdCard() ..... ");
        Intent intent = new Intent(context, wintone.idcard.android.RecogService.class);
        if (recogIdConn == null) {
            recogIdConn = new IdServiceConnection();
        }
        context.bindService(intent, recogIdConn, Service.BIND_AUTO_CREATE);
    }

    private class IdServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            String packageName = name.getPackageName();
            Zzlog.out(TAG, "package name = " + packageName);

            recogIDBinder = (recogBinder) service;

            RecogParameterMessage rpm = new RecogParameterMessage();

            Zzlog.out(TAG, "RecogParameterMessage ");

            rpm.nMainID = getMainId();
            rpm.nTypeLoadImageToMemory = 0;
            rpm.nSubID = null;
            rpm.GetSubID = true;
            rpm.GetVersionInfo = true;
            rpm.logo = "";
            rpm.userdata = "";
            rpm.sn = "";
            rpm.authfile = "";
            rpm.isCut = false;
            rpm.triggertype = 0;
            rpm.devcode = Constant.DEVCODE;
            rpm.isOnlyClassIDCard = true;
            if (rpm.nMainID == 2) {
                rpm.isAutoClassify = true;
            }
            // pic data
            rpm.lpFileName = tempPic; // rpm.lpFileName

            ResultMessage resultMessage = null;

            String recogResultString = "";

            try {
                resultMessage = recogIDBinder.getRecogResult(rpm);

                if (resultMessage.ReturnAuthority == 0 && resultMessage.ReturnInitIDCard == 0
                        && resultMessage.ReturnLoadImageToMemory == 0 && resultMessage.ReturnRecogIDCard > 0) {

                    String[] GetFieldName = resultMessage.GetFieldName;
                    String[] GetRecogResult = resultMessage.GetRecogResult;

                    for (int i = 1; i < GetFieldName.length; i++) {

                        if (GetRecogResult[i] != null) {
                            if (!recogResultString.equals(""))
                                recogResultString = recogResultString + GetFieldName[i] + ":" + GetRecogResult[i] + ",";
                            else {
                                recogResultString = GetFieldName[i] + ":" + GetRecogResult[i] + ",";
                            }
                            // Zzlog.out(TAG, "i = " + i + ", " + GetFieldName[i] + ":" + GetRecogResult[i]);
                        }
                    }

                    Zzlog.out(TAG, "recogResultString:" + recogResultString);
                    setResult(recogResultString, "");

                } else {
                    setErrorResult(-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                context.unbindService(this);                
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            recogIDBinder = null;
        }
    }
    

    
    protected void getPicProperties() {
        Bitmap bitmap = BitmapFactory.decodeFile(picPath);

        if (picType == CAR_PLATE) {
            height = 900;
            width = 800;
            bitmap = cutPicture(bitmap, width, height);
        } else { // id card

            bitmap = cutPicture(bitmap, MAX_ID_WIDTH, MAX_ID_HEIGHT);
        }

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        Zzlog.out(TAG, "width = " + width + ", height = " + height);

        FileUtils.savePicture(tempPic, bitmap);

        recogBitmap = bitmap;
    }

    private Bitmap cutPicture(Bitmap bitmap, int w, int h) {
        int wid = bitmap.getWidth();
        int hei = bitmap.getHeight();
        if (wid > w) {
            hei = w * hei / wid;
            wid = w;
            bitmap = Bitmap.createScaledBitmap(bitmap, wid, hei, true);
        }
        if (hei > h) {
            int top = (hei - h) / 2;
            hei = h;
            bitmap = Bitmap.createBitmap(bitmap, 0, top, wid, hei);
        }

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        Zzlog.out(TAG, "width = " + width + ", height = " + height);
        return bitmap;
    }

    private int getMainId() {

        int id = 0;
        if (picType == ID_CARD) { // id card
            id = 2;
        } else if (picType == DRIVER_LICENSE) { // driver license.
            id = 5;
        } else if (picType == PASSPORT) { // passport
            id = 13;
        }

        return id;
    }

    private void recognizeCarplate() {
        Zzlog.out(TAG, "recognizeCarplate ..... ");
        Intent intent = new Intent(context, RecogService.class);
        context.bindService(intent, recogConn, Service.BIND_AUTO_CREATE);
    }

    public ServiceConnection recogConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Zzlog.out(TAG, "onServiceConnected()! !");
            try {
                recogCarPlateBinder = (RecogService.MyBinder)service;

                int iInitPlateIDSDK = recogCarPlateBinder.getInitPlateIDSDK();

                Zzlog.out(TAG, "onServiceConnected .. iInitPlateIDSDK = " + iInitPlateIDSDK);

                if (iInitPlateIDSDK != 0) {
                    setErrorResult(iInitPlateIDSDK);
                }

                PlateCfgParameter cfgparameter = new PlateCfgParameter();
                cfgparameter.armpolice = 4;
                cfgparameter.armpolice2 = 16;
                cfgparameter.embassy = 12;
                cfgparameter.individual = 0;

                cfgparameter.nOCR_Th = 0;
                cfgparameter.nPlateLocate_Th = 5;
                cfgparameter.onlylocation = 15;
                cfgparameter.tworowyellow = 2;
                cfgparameter.tworowarmy = 6;
                cfgparameter.szProvince = "";
                cfgparameter.onlytworowyellow = 11;
                cfgparameter.tractor = 8;
                cfgparameter.bIsNight = 1;

                int imageformat = 1; // JPG
                int bVertFlip = 0;
                int bDwordAligned = 1;
                recogCarPlateBinder.setRecogArgu(cfgparameter, imageformat, bVertFlip, bDwordAligned);
                String[] fieldvalue = null;
                int nRet = -1;

                PlateRecognitionParameter prp = new PlateRecognitionParameter();

                prp.pic = tempPic;
                Zzlog.out(TAG, "tempPic = " + tempPic + ", width = " + width + ", height = " + height);
                prp.devCode = Constant.DEVCODE;
                fieldvalue = recogCarPlateBinder.doRecogDetail(prp);
                nRet = recogCarPlateBinder.getnRet();

                if (nRet != 0) {
                    Zzlog.out(TAG, " nRet = " + nRet);
                    setErrorResult(nRet);
                } else {
                    if (fieldvalue != null) {
                        getResult(fieldvalue);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                context.unbindService(recogConn);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Zzlog.out(TAG, "onServiceDisconnected()! !");
            recogCarPlateBinder = null;
        }
    };

    public void clean() {
        recogCarPlateBinder = null;
        recogIDBinder = null;
        recogIdConn = null;
    }


    private void getResult(String[] fieldvalue) {

        Zzlog.out(TAG, "getResult: ......................");

        if (fieldvalue != null && fieldvalue.length > 0) {
            for (int i = 0; i < fieldvalue.length; i++) {
                Zzlog.out(TAG, "fieldvalue[" + i + "] = " + fieldvalue[i]);
            }
        } else {
            Zzlog.out(TAG, "fieldvalue id empty!");
        }

        String[] resultString;
        String boolString = "";
        boolString = fieldvalue[0];

        if (!TextUtils.isEmpty(boolString)) {

            resultString = boolString.split(";");
            int length = resultString.length;
            String firstNumber = null;
            if (length > 0) {
                firstNumber = resultString[0];
                for (int i = 0; i < length; i++) {
                    Zzlog.out(TAG, "resultString[" + i + "] = " + resultString[i]);
                }
            }

            String color = null;
            if (!TextUtils.isEmpty(firstNumber)) {
                if (fieldvalue.length > 2) {
                    color = fieldvalue[1];
                }

                Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(300);
            }

            String recongnizeResult = "车牌：" + firstNumber + "; 颜色：" + color;
            
            Gson gson = new Gson();
            
            CarBean car = new CarBean();
            car.setColor(color);
            car.setNumber(firstNumber);
            
            CacheBean.jsonbean = gson.toJson(car);
            
            setResult(recongnizeResult, firstNumber);
        } else {
            setErrorResult(-2);
        }
        fieldvalue = null;
    }

    private void setResult(String recongnizeResult, String keyNumber) {
        if (recognizerInterface != null) {
            recognizerInterface.onRecognizeSucceed(recongnizeResult, keyNumber);
        }
    }

    protected void setErrorResult(int errorCode) {
        if (-2 == errorCode) {
            String message = "没有发现号码";
            Zzlog.out(TAG, "message = " + message);
        }
        if (recognizerInterface != null) {
            recognizerInterface.onRecognizeFailed(errorCode);
        }
    }

}