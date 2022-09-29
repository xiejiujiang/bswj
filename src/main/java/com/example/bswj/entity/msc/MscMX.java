package com.example.bswj.entity.msc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class MscMX extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String spmc;//商品名称

    @ExcelProperty(index = 1)
    private String ean;//EAN

    @ExcelProperty(index = 2)
    private String sku;//SKU

    @ExcelProperty(index = 3)
    private String skun;//SKUN

    @ExcelProperty(index = 4)
    private String dw;//单位

    @ExcelProperty(index = 5)
    private String yssl;//应收数量

    @ExcelProperty(index = 6)
    private String sssl;//实收数量

    @ExcelProperty(index = 7)
    private String rkck;//入库仓库

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkun() {
        return skun;
    }

    public void setSkun(String skun) {
        this.skun = skun;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getYssl() {
        return yssl;
    }

    public void setYssl(String yssl) {
        this.yssl = yssl;
    }

    public String getSssl() {
        return sssl;
    }

    public void setSssl(String sssl) {
        this.sssl = sssl;
    }

    public String getRkck() {
        return rkck;
    }

    public void setRkck(String rkck) {
        this.rkck = rkck;
    }

    @Override
    public String toString() {
        return "MscMX{" +
                "spmc='" + spmc + '\'' +
                ", ean='" + ean + '\'' +
                ", sku='" + sku + '\'' +
                ", skun='" + skun + '\'' +
                ", dw='" + dw + '\'' +
                ", yssl='" + yssl + '\'' +
                ", sssl='" + sssl + '\'' +
                ", rkck='" + rkck + '\'' +
                '}';
    }
}
