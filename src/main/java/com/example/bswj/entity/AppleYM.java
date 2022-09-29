package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class AppleYM extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String fhkw;//发货库位

    @ExcelProperty(index = 1)
    private String district;//区域

    @ExcelProperty(index = 2)
    private String mcbzmc;//卖场标准名称

    @ExcelProperty(index = 3)
    private String mddm;//门店代码

    @ExcelProperty(index = 4)
    private String po;//PO

    @ExcelProperty(index = 5)
    private String ordernmb;//系统单号

    @ExcelProperty(index = 6)
    private String cpbm;//产品编码

    @ExcelProperty(index = 7)
    private String xh;//型号

    @ExcelProperty(index = 8)
    private String sku;//SKU

    @ExcelProperty(index = 9)
    private String sl;//数量

    @ExcelProperty(index = 10)
    private String fhsl;//发货数量

    @ExcelProperty(index = 11)
    private String cy;//差异

    @ExcelProperty(index = 12)
    private String price;//含税单价

    @ExcelProperty(index = 13)
    private String amount;//含税金额

    public String getFhkw() {
        return fhkw;
    }

    public void setFhkw(String fhkw) {
        this.fhkw = fhkw;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMcbzmc() {
        return mcbzmc;
    }

    public void setMcbzmc(String mcbzmc) {
        this.mcbzmc = mcbzmc;
    }

    public String getMddm() {
        return mddm;
    }

    public void setMddm(String mddm) {
        this.mddm = mddm;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getOrdernmb() {
        return ordernmb;
    }

    public void setOrdernmb(String ordernmb) {
        this.ordernmb = ordernmb;
    }

    public String getCpbm() {
        return cpbm;
    }

    public void setCpbm(String cpbm) {
        this.cpbm = cpbm;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getFhsl() {
        return fhsl;
    }

    public void setFhsl(String fhsl) {
        this.fhsl = fhsl;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AppleYM{" +
                "fhkw='" + fhkw + '\'' +
                ", district='" + district + '\'' +
                ", mcbzmc='" + mcbzmc + '\'' +
                ", mddm='" + mddm + '\'' +
                ", po='" + po + '\'' +
                ", ordernmb='" + ordernmb + '\'' +
                ", cpbm='" + cpbm + '\'' +
                ", xh='" + xh + '\'' +
                ", sku='" + sku + '\'' +
                ", sl='" + sl + '\'' +
                ", fhsl='" + fhsl + '\'' +
                ", cy='" + cy + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}