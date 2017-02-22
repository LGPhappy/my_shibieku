package com.xdja.zdsb.bean;

import java.io.Serializable;

/**
 * 驾驶证相关属性
 * @author Administrator
 *
 */
public class DriverIDCard implements Serializable {

    private static final long serialVersionUID = 1L;
    private String blzd; //保留字段
    private String xm; //姓名
    private String xb; //性别
    private String sfzh; //身份证号
    private String csrq; //出生日期
    private String csdz; //出生地址
    private String cslzrq; //初始领证日期
    private String zjcx; //准驾车型
    private String yxqqsrq; //有效期起始日期
    private String yxqx; //有效期限

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
    public String getSfzh() {
        return sfzh;
    }
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
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
    public String getCslzrq() {
        return cslzrq;
    }
    public void setCslzrq(String cslzrq) {
        this.cslzrq = cslzrq;
    }
    public String getZjcx() {
        return zjcx;
    }
    public void setZjcx(String zjcx) {
        this.zjcx = zjcx;
    }
    public String getYxqqsrq() {
        return yxqqsrq;
    }
    public void setYxqqsrq(String yxqqsrq) {
        this.yxqqsrq = yxqqsrq;
    }
    public String getYxqx() {
        return yxqx;
    }
    public void setYxqx(String yxqx) {
        this.yxqx = yxqx;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
