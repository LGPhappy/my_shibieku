package com.ocrlib.zdsb.auth;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;


public class RecognizerAuth {
    
    private static List<String> list = new ArrayList<String>();

    private static List<String> getAuthList() {
        
        if (list.size() == 0) {
            for(String aa: AuthConstant.authString) {
                list.add(aa);
            }
        }
        return list;
    }
    
    public static boolean check(Intent intent) {
        if (AuthConstant.isNeedAuth) {
            String name = intent.getStringExtra("packagename");
            return check(name);
        } else {
            return true;
        }

    }
    
    
    public static boolean check(String authString) {
        if (!TextUtils.isEmpty(authString)) {
            String code = string2MD5(AuthConstant.MD5CODE);
            code = code + string2MD5(authString);
            if (getAuthList().contains(code)) {
                return true;
            }
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
        byte[] byteArray = inStr.getBytes(); 
        byte[] md5Bytes = md5.digest(byteArray);

        return byte2String (md5Bytes);
    }  
    
    public static String byte2String(byte[] data){

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < data.length; i++){
            int val = ((int) data[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));  
        }
        return hexValue.toString();
    }
    
}
