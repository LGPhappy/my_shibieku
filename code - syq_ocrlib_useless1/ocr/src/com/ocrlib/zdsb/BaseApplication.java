package com.ocrlib.zdsb;

import com.ocrlib.zdsb.utils.Zzlog;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Zzlog.init();
    }
}
