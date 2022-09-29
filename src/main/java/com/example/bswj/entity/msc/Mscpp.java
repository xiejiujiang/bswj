package com.example.bswj.entity.msc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Mscpp extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String dpmc;//店铺名称

    @ExcelProperty(index = 1)
    private String spmc;//商品名称

    @ExcelProperty(index = 2)
    private String spbm;//商品编码

    @ExcelProperty(index = 3)
    private String color;//颜色

    @ExcelProperty(index = 4)
    private String spzt;//商品状态

    @ExcelProperty(index = 5)
    private String jzthj;//基准提货价

    @ExcelProperty(index = 6)
    private String wflje;//无条件单台返利金额

    @ExcelProperty(index = 7)
    private String yflje;//有条件最高单台返利金额

    @ExcelProperty(index = 8)
    private String jgsxrq;//价格生效日期

    public String getDpmc() {
        return dpmc;
    }

    public void setDpmc(String dpmc) {
        this.dpmc = dpmc;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getJzthj() {
        return jzthj;
    }

    public void setJzthj(String jzthj) {
        this.jzthj = jzthj;
    }

    public String getWflje() {
        return wflje;
    }

    public void setWflje(String wflje) {
        this.wflje = wflje;
    }

    public String getYflje() {
        return yflje;
    }

    public void setYflje(String yflje) {
        this.yflje = yflje;
    }

    public String getJgsxrq() {
        return jgsxrq;
    }

    public void setJgsxrq(String jgsxrq) {
        this.jgsxrq = jgsxrq;
    }

    @Override
    public String toString() {
        return "Mscpp{" +
                "dpmc='" + dpmc + '\'' +
                ", spmc='" + spmc + '\'' +
                ", spbm='" + spbm + '\'' +
                ", color='" + color + '\'' +
                ", spzt='" + spzt + '\'' +
                ", jzthj='" + jzthj + '\'' +
                ", wflje='" + wflje + '\'' +
                ", yflje='" + yflje + '\'' +
                ", jgsxrq='" + jgsxrq + '\'' +
                '}';
    }
}
