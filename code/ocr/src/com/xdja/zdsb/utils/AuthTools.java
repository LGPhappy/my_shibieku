package com.xdja.zdsb.utils;


import java.security.MessageDigest;

import com.wintone.plateid.AuthService;
import com.wintone.plateid.PlateAuthParameter;
import com.xdja.zdsb.R;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.IBinder;
import android.widget.Toast;

public class AuthTools {

    protected static final String TAG = "AuthTools";
    private Context context;
    private AuthInterface authInterface;
    private int ReturnAuthority = -1;
    private AuthService.MyBinder authBinder;

    public AuthTools (Context context, AuthInterface authInterface) {
        this.context = context;
        this.authInterface = authInterface;
    }

    public void start() {
        Intent authIntent = new Intent(context, AuthService.class);
        
        boolean b = context.bindService(authIntent, authConn, Service.BIND_AUTO_CREATE);
        if (b) {
            Zzlog.out(TAG, "bind service()");    
        } else {
            Zzlog.out(TAG, "bind service() failed!!!");
        }
    }

    private ServiceConnection authConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            authBinder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Zzlog.out(TAG, "onServiceConnected()");

            authBinder = (AuthService.MyBinder) service;
            Toast.makeText(context, R.string.auth_check_service_bind_success, Toast.LENGTH_SHORT).show();
            try {
                PlateAuthParameter pap = new PlateAuthParameter();
                pap.sn = Constant.DEVCODE;
                pap.devCode = Constant.DEVCODE;
                ReturnAuthority = authBinder.getAuth(pap);

                Zzlog.out(TAG, "onServiceConnected() ReturnAuthority = " + ReturnAuthority);

                if (authInterface != null) {
                    if (ReturnAuthority != 0) {
                        authInterface.onAuthFailed();
                    } else {
                        authInterface.onAuthSucceed();
                    }
                }
            }catch (Exception e) {
                if (authInterface != null) authInterface.onAuthFailed();
                e.printStackTrace();
            }finally{
                if (authBinder != null) {
                    context.unbindService(authConn);
                }
            }
        }
    };

    public static boolean isAuthenticated (String authcode) {
        
        String code = string2MD5(Constant.MD5CODE);
        String packageName = string2MD5(Constant.PACKAGE_NAME);

        if (authcode.equals(code + packageName)) {
            return true;
        }

        return false;
    }

    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
    
}
