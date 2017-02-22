package com.xdja.zdsb;



import java.io.FileNotFoundException;
import java.io.IOException;

import com.xdja.ocrjar.core.RecognizerInterface;
import com.xdja.ocrjar.core.Zzlog;
import com.xdja.zdsb.recognize.JarInvoke;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    protected static final String TAG = "MainActivity";

    private static final int GET_IMAGE_ON_DEVICE = 6; // get image file from devices.

    int picType; 

    private AlertDialog alertDialog; 

    private String takePicFileName = FileUtils.PATH + "temp.jpg";

    JarInvoke jarInvoke;
    
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView2 = (TextView) findViewById(R.id.textView2);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case GET_IMAGE_ON_DEVICE:
                String filename = getPictureFromDevice(data);

                jarInvoke = new JarInvoke(this);

                jarInvoke.picPath = filename;
                jarInvoke.type = picType;
                jarInvoke.recognizerInterface = recognizerInterface;

                jarInvoke.recognizer();

                break;
            default:
                Zzlog.out(TAG, "onActivityResult.... default");
            }
        }
    }

    private String getPictureFromDevice(Intent data) {
        ContentResolver resolver = this.getContentResolver();
        String[] projection = { MediaStore.Images.Media.DATA};
        Uri uri = data.getData();
        CursorLoader cursorLoader = new CursorLoader(this, uri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(projection[0]));

        Zzlog.out(TAG, "path  = " + path);

        if (TextUtils.isEmpty(path)) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, uri);
                path = FileUtils.getStringFileName(FileUtils.MEDIA_TYPE_IMAGE);

                FileUtils.savePicture(path, bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Zzlog.eOut(e);
            } catch (IOException e) {
                e.printStackTrace();
                Zzlog.eOut(e);
            }
        }
        takePicFileName = path;

        Zzlog.out(TAG, "takePicFilename = " + takePicFileName);

        return takePicFileName;
    }

    
    public void onClickButton(View view) {
        String[] items = new String[] { "车牌", "身份证", "驾照", "护照" };
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Zzlog.out(TAG, "which = " + which);
                picType = which + 1;

                alertDialog.dismiss();
                alertDialog = null;

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_IMAGE_ON_DEVICE);
            }
        };
        alertDialog = new AlertDialog.Builder(this).setTitle("选择图片类型：")
                .setSingleChoiceItems(items, -1, listener).show();
    }
    
    private RecognizerInterface recognizerInterface = new RecognizerInterface(){

        @Override
        public void onRecognizeSucceed(String result, String keyNumber) {
            textView2.setText(result); 
            Zzlog.out(TAG, "result = " + result);
        }
        @Override
        public void onRecognizeFailed(int errorCode) {
            textView2.setText(errorCode);
            Zzlog.out(TAG, "errorCode = " + errorCode);
        }
    };

}
