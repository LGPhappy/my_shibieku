package com.xdja.zdsb.bean;

import java.io.Serializable;

/**
 * 第二代身份证扫描结果bean
 * @author lwj
 *
 */
public class SecondIDCard implements Serializable{

    private static final long serialVersionUID = 1L;
    private String blzd; //保留字段
    private String xm; //姓名
    private String xb; //性别
    private String mz; //名族
    private String csrq; //出生日期
    private String csdz; //出生地址
    private String sfzh; //身份证号

    public String getBlzd() {
        return blzd;
    }
    public void setBlzd(String blzd) {
        this.blzd = blzd;
    }
    public String getXm() {
        return xm;
    }
    public void setXm(String xm) {
        this.xm = xm;
    }
    public String getXb() {
        return xb;
    }
    public void setXb(String xb) {
        this.xb = xb;
    }
    public String getMz() {
        return mz;
    }
    public void setMz(String mz) {
        this.mz = mz;
    }
    public String getCsrq() {
        return csrq;
    }
    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }
    public String getCsdz() {
        return csdz;
    }
    public void setCsdz(String csdz) {
        this.csdz = csdz;
    }
    public String getSfzh() {
        return sfzh;
    }
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }
}
