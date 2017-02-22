package xdja.ocr.authentication;

import java.security.MessageDigest;

public class MainAuth {

    public static void main(String[] args) {
        String code = string2MD5(AuthConstant.MD5CODE);
        String packageName = string2MD5(AuthConstant.PACKAGE_NAME);

        String packageName1 = string2MD5(AuthConstant.PACKAGE_NAME1);
        String packageName2 = string2MD5(AuthConstant.PACKAGE_NAME2);
        
        System.out.println("md5 String = \"" + code + packageName + "\"");
        System.out.println("md5 String = \"" + code + packageName1 + "\"");
        System.out.println("md5 String = \"" + code + packageName2 + "\"");
        
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
