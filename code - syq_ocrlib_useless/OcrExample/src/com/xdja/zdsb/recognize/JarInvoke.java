package com.xdja.zdsb.recognize;

import com.xdja.ocrjar.core.PictureRecognize;
import com.xdja.ocrjar.core.RecognizerInterface;
import com.xdja.ocrjar.core.Zzlog;

import android.content.Context;

public class JarInvoke {

    protected static final String TAG = "JarInvoke";

    private Context context;
    
    public String picPath;

    public int type;
    
    public JarInvoke(Context context) {
        this.context = context;
    }

    public RecognizerInterface recognizerInterface = new RecognizerInterface(){

        @Override
        public void onRecognizeSucceed(String result, String keyNumber) {
            Zzlog.out(TAG, "result = " + result);
        }
        @Override
        public void onRecognizeFailed(int errorCode) {
            Zzlog.out(TAG, "errorCode = " + errorCode);
        }
    };

    public void recognizer(){

        PictureRecognize pictureRecognize = new PictureRecognize (context);

        pictureRecognize.doRecognize(picPath, type, recognizerInterface);
    }

}
