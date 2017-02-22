package com.xdja.zdsb.utils;


public interface RecognizerInterface {
    public void onRecognizeSucceed(String result, String keyNumber);
    public void onRecognizeFailed(int errorCode);
}
