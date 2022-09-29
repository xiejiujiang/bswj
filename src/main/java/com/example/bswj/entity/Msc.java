package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Msc extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String ordernmb;//订单编号

    @ExcelProperty(index = 1)
    private String orderje;//订单金额

    @ExcelProperty(index = 2)
    private String jldkje;//激励抵扣金额

    @ExcelProperty(index = 3)
    private String xszfje;//线上支付金额

    @ExcelProperty(index = 4)//币种
    private String bz;

    @ExcelProperty(index = 5)//订单状态
    private String ddzt;

    @ExcelProperty(index = 6)//支付时间
    private String zfsj;

    @ExcelProperty(index = 7)//客户
    private String kehu;

    @ExcelProperty(index = 8)//店铺
    private String dianpu;

    @ExcelProperty(index = 9)//省
    private String sheng;

    @ExcelProperty(index = 10)//返利
    private String fanli;

    @ExcelProperty(index = 11)//价保
    private String jiabao;

    @ExcelProperty(index = 12)//临时激励
    private String lsjl;

    @ExcelProperty(index = 13)//关联单号
    private String gldh;

    @ExcelProperty(index = 14)//创建时间
    private String createtime;

    @ExcelProperty(index = 15)//订单版本
    private String ddbb;

    @ExcelProperty(index = 16)//地址
    private String address;

    @ExcelProperty(index = 17)//联系人
    private String lxr;

    @ExcelProperty(index = 18)//手机
    private String mobile;

    @ExcelProperty(index = 18)//订单类型
    private String ddlx;

    @ExcelProperty(index = 19)//订单来源
    private String ddly;

    @ExcelProperty(index = 20)//订单行号
    private String ddhh;

    @ExcelProperty(index = 21)//商品型号编码
    private String spxhbm;

    @ExcelProperty(index = 22)//商品型号
    private String spxh;

    @ExcelProperty(index = 23)//product_model
    private String productmodel;

    @ExcelProperty(index = 24)//一级目录
    private String yjml;

    @ExcelProperty(index = 25)//二级目录
    private String ejml;

    @ExcelProperty(index = 26)//商品编码
    private String spbm;

    @ExcelProperty(index = 27)//产品编码
    private String cpbm;

    @ExcelProperty(index = 28)//店铺商品编码
    private String dpspbm;

    @ExcelProperty(index = 29)//商品名称
    private String spmc;

    @ExcelProperty(index = 30)//取消原因
    private String qxyy;

    @ExcelProperty(index = 31)//价格
    private String price;

    @ExcelProperty(index = 32)//满减金额
    private String mjje;

    @ExcelProperty(index = 33)//首销前价格标识
    private String sxqjgbz;

    @ExcelProperty(index = 34)//数量
    private String sl;

    @ExcelProperty(index = 35)//小计
    private String xiaoji;

    @ExcelProperty(index = 36)//商品用途
    private String spyt;

    public String getOrdernmb() {
        return ordernmb;
    }

    public void setOrdernmb(String ordernmb) {
        this.ordernmb = ordernmb;
    }

    public String getOrderje() {
        return orderje;
    }

    public void setOrderje(String orderje) {
        this.orderje = orderje;
    }

    public String getJldkje() {
        return jldkje;
    }

    public void setJldkje(String jldkje) {
        this.jldkje = jldkje;
    }

    public String getXszfje() {
        return xszfje;
    }

    public void setXszfje(String xszfje) {
        this.xszfje = xszfje;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDdzt() {
        return ddzt;
    }

    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    public String getZfsj() {
        return zfsj;
    }

    public void setZfsj(String zfsj) {
        this.zfsj = zfsj;
    }

    public String getKehu() {
        return kehu;
    }

    public void setKehu(String kehu) {
        this.kehu = kehu;
    }

    public String getDianpu() {
        return dianpu;
    }

    public void setDianpu(String dianpu) {
        this.dianpu = dianpu;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getFanli() {
        return fanli;
    }

    public void setFanli(String fanli) {
        this.fanli = fanli;
    }

    public String getJiabao() {
        return jiabao;
    }

    public void setJiabao(String jiabao) {
        this.jiabao = jiabao;
    }

    public String getLsjl() {
        return lsjl;
    }

    public void setLsjl(String lsjl) {
        this.lsjl = lsjl;
    }

    public String getGldh() {
        return gldh;
    }

    public void setGldh(String gldh) {
        this.gldh = gldh;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDdbb() {
        return ddbb;
    }

    public void setDdbb(String ddbb) {
        this.ddbb = ddbb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDdlx() {
        return ddlx;
    }

    public void setDdlx(String ddlx) {
        this.ddlx = ddlx;
    }

    public String getDdly() {
        return ddly;
    }

    public void setDdly(String ddly) {
        this.ddly = ddly;
    }

    public String getDdhh() {
        return ddhh;
    }

    public void setDdhh(String ddhh) {
        this.ddhh = ddhh;
    }

    public String getSpxhbm() {
        return spxhbm;
    }

    public void setSpxhbm(String spxhbm) {
        this.spxhbm = spxhbm;
    }

    public String getSpxh() {
        return spxh;
    }

    public void setSpxh(String spxh) {
        this.spxh = spxh;
    }

    public String getProductmodel() {
        return productmodel;
    }

    public void setProductmodel(String productmodel) {
        this.productmodel = productmodel;
    }

    public String getYjml() {
        return yjml;
    }

    public void setYjml(String yjml) {
        this.yjml = yjml;
    }

    public String getEjml() {
        return ejml;
    }

    public void setEjml(String ejml) {
        this.ejml = ejml;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getCpbm() {
        return cpbm;
    }

    public void setCpbm(String cpbm) {
        this.cpbm = cpbm;
    }

    public String getDpspbm() {
        return dpspbm;
    }

    public void setDpspbm(String dpspbm) {
        this.dpspbm = dpspbm;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getQxyy() {
        return qxyy;
    }

    public void setQxyy(String qxyy) {
        this.qxyy = qxyy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMjje() {
        return mjje;
    }

    public void setMjje(String mjje) {
        this.mjje = mjje;
    }

    public String getSxqjgbz() {
        return sxqjgbz;
    }

    public void setSxqjgbz(String sxqjgbz) {
        this.sxqjgbz = sxqjgbz;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getXiaoji() {
        return xiaoji;
    }

    public void setXiaoji(String xiaoji) {
        this.xiaoji = xiaoji;
    }

    public String getSpyt() {
        return spyt;
    }

    public void setSpyt(String spyt) {
        this.spyt = spyt;
    }

    @Override
    public String toString() {
        return "Msc{" +
                "ordernmb='" + ordernmb + '\'' +
                ", orderje='" + orderje + '\'' +
                ", jldkje='" + jldkje + '\'' +
                ", xszfje='" + xszfje + '\'' +
                ", bz='" + bz + '\'' +
                ", ddzt='" + ddzt + '\'' +
                ", zfsj='" + zfsj + '\'' +
                ", kehu='" + kehu + '\'' +
                ", dianpu='" + dianpu + '\'' +
                ", sheng='" + sheng + '\'' +
                ", fanli='" + fanli + '\'' +
                ", jiabao='" + jiabao + '\'' +
                ", lsjl='" + lsjl + '\'' +
                ", gldh='" + gldh + '\'' +
                ", createtime='" + createtime + '\'' +
                ", ddbb='" + ddbb + '\'' +
                ", address='" + address + '\'' +
                ", lxr='" + lxr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ddlx='" + ddlx + '\'' +
                ", ddly='" + ddly + '\'' +
                ", ddhh='" + ddhh + '\'' +
                ", spxhbm='" + spxhbm + '\'' +
                ", spxh='" + spxh + '\'' +
                ", productmodel='" + productmodel + '\'' +
                ", yjml='" + yjml + '\'' +
                ", ejml='" + ejml + '\'' +
                ", spbm='" + spbm + '\'' +
                ", cpbm='" + cpbm + '\'' +
                ", dpspbm='" + dpspbm + '\'' +
                ", spmc='" + spmc + '\'' +
                ", qxyy='" + qxyy + '\'' +
                ", price='" + price + '\'' +
                ", mjje='" + mjje + '\'' +
                ", sxqjgbz='" + sxqjgbz + '\'' +
                ", sl='" + sl + '\'' +
                ", xiaoji='" + xiaoji + '\'' +
                ", spyt='" + spyt + '\'' +
                '}';
    }
}
