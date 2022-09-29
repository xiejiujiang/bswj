package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Putian extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String ordernmb;//订单编号

    @ExcelProperty(index = 1)
    private String fgs;//分公司

    @ExcelProperty(index = 2)
    private String orderctm;//订单创建时间

    @ExcelProperty(index = 3)
    private String orderstate;//订单状态

    @ExcelProperty(index = 4)
    private String lb;//类别

    @ExcelProperty(index = 5)
    private String pp;//品牌

    @ExcelProperty(index = 6)
    private String xm;//项目

    @ExcelProperty(index = 7)
    private String jx;//机型

    @ExcelProperty(index = 8)
    private String ys;//颜色

    @ExcelProperty(index = 9)
    private String spdj;//商品单价

    @ExcelProperty(index = 10)
    private String spsl;//商品数量

    @ExcelProperty(index = 11)
    private String hjje;//合计金额

    @ExcelProperty(index = 12)
    private String cksl;//出库数量

    @ExcelProperty(index = 13)
    private String shsl;//收货数量

    @ExcelProperty(index = 14)
    private String jssl;//拒收数量

    @ExcelProperty(index = 15)
    private String address;//收获地址

    @ExcelProperty(index = 16)
    private String beizhu;//备注

    public String getOrdernmb() {
        return ordernmb;
    }

    public void setOrdernmb(String ordernmb) {
        this.ordernmb = ordernmb;
    }

    public String getFgs() {
        return fgs;
    }

    public void setFgs(String fgs) {
        this.fgs = fgs;
    }

    public String getOrderctm() {
        return orderctm;
    }

    public void setOrderctm(String orderctm) {
        this.orderctm = orderctm;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    public String getSpdj() {
        return spdj;
    }

    public void setSpdj(String spdj) {
        this.spdj = spdj;
    }

    public String getSpsl() {
        return spsl;
    }

    public void setSpsl(String spsl) {
        this.spsl = spsl;
    }

    public String getHjje() {
        return hjje;
    }

    public void setHjje(String hjje) {
        this.hjje = hjje;
    }

    public String getCksl() {
        return cksl;
    }

    public void setCksl(String cksl) {
        this.cksl = cksl;
    }

    public String getShsl() {
        return shsl;
    }

    public void setShsl(String shsl) {
        this.shsl = shsl;
    }

    public String getJssl() {
        return jssl;
    }

    public void setJssl(String jssl) {
        this.jssl = jssl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    @Override
    public String toString() {
        return "Putian{" +
                "ordernmb='" + ordernmb + '\'' +
                ", fgs='" + fgs + '\'' +
                ", orderctm='" + orderctm + '\'' +
                ", orderstate='" + orderstate + '\'' +
                ", lb='" + lb + '\'' +
                ", pp='" + pp + '\'' +
                ", xm='" + xm + '\'' +
                ", jx='" + jx + '\'' +
                ", ys='" + ys + '\'' +
                ", spdj='" + spdj + '\'' +
                ", spsl='" + spsl + '\'' +
                ", hjje='" + hjje + '\'' +
                ", cksl='" + cksl + '\'' +
                ", shsl='" + shsl + '\'' +
                ", jssl='" + jssl + '\'' +
                ", address='" + address + '\'' +
                ", beizhu='" + beizhu + '\'' +
                '}';
    }
}
