package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Zyou extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String ordernmb;//订单编号

    @ExcelProperty(index = 1)
    private String wbordernmb;//外部订单编号

    @ExcelProperty(index = 2)
    private String bussytype;//业务类型

    @ExcelProperty(index = 3)
    private String orderctm;//订单创建时间

    @ExcelProperty(index = 4)
    private String salearea;//销售区域

    @ExcelProperty(index = 5)
    private String orderstate;//订单状态

    @ExcelProperty(index = 6)//商品编码
    private String spbm;

    @ExcelProperty(index = 7)//商品编码
    private String spmc;

    @ExcelProperty(index = 8)//原订货数量
    private String ydhsl;

    @ExcelProperty(index = 9)//商品单价
    private String spdj;

    @ExcelProperty(index = 10)//实际订单总额
    private String sjddze;

    @ExcelProperty(index = 11)//运费
    private String yf;

    @ExcelProperty(index = 12)//支付金额
    private String zfje;

    @ExcelProperty(index = 13)//使用折让
    private String syzr;

    @ExcelProperty(index = 14)//待发运信用金额
    private String dfyxyje;

    @ExcelProperty(index = 15)//支付时间
    private String zfsj;

    @ExcelProperty(index = 16)//支付方式
    private String zffs;

    @ExcelProperty(index = 17)//支付状态
    private String zfzt;

    @ExcelProperty(index = 18)//开票类型
    private String kplx;

    @ExcelProperty(index = 19)//商品行状态
    private String sphzt;

    @ExcelProperty(index = 20)//撤货数
    private String chs;

    @ExcelProperty(index = 21)//撤货释放金额
    private String chsfje;

    @ExcelProperty(index = 22)//实际订货数
    private String sjdhs;

    @ExcelProperty(index = 23)//已发货数
    private String yfhs;

    @ExcelProperty(index = 24)//签收数
    private String qss;

    @ExcelProperty(index = 25)//退货数
    private String ths;

    @ExcelProperty(index = 26)//未发货数
    private String wfhs;

    @ExcelProperty(index = 27)//未发货金额
    private String wfhje;

    @ExcelProperty(index = 28)//发运方式
    private String fyfs;

    @ExcelProperty(index = 29)//提货人/收货人
    private String thrshr;

    @ExcelProperty(index = 30)//车牌号
    private String cph;

    @ExcelProperty(index = 31)//地址编码
    private String dzbm;

    @ExcelProperty(index = 32)//收货地址
    private String shdz;

    public String getOrdernmb() {
        return ordernmb;
    }

    public void setOrdernmb(String ordernmb) {
        this.ordernmb = ordernmb;
    }

    public String getWbordernmb() {
        return wbordernmb;
    }

    public void setWbordernmb(String wbordernmb) {
        this.wbordernmb = wbordernmb;
    }

    public String getBussytype() {
        return bussytype;
    }

    public void setBussytype(String bussytype) {
        this.bussytype = bussytype;
    }

    public String getOrderctm() {
        return orderctm;
    }

    public void setOrderctm(String orderctm) {
        this.orderctm = orderctm;
    }

    public String getSalearea() {
        return salearea;
    }

    public void setSalearea(String salearea) {
        this.salearea = salearea;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getYdhsl() {
        return ydhsl;
    }

    public void setYdhsl(String ydhsl) {
        this.ydhsl = ydhsl;
    }

    public String getSpdj() {
        return spdj;
    }

    public void setSpdj(String spdj) {
        this.spdj = spdj;
    }

    public String getSjddze() {
        return sjddze;
    }

    public void setSjddze(String sjddze) {
        this.sjddze = sjddze;
    }

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf;
    }

    public String getZfje() {
        return zfje;
    }

    public void setZfje(String zfje) {
        this.zfje = zfje;
    }

    public String getSyzr() {
        return syzr;
    }

    public void setSyzr(String syzr) {
        this.syzr = syzr;
    }

    public String getDfyxyje() {
        return dfyxyje;
    }

    public void setDfyxyje(String dfyxyje) {
        this.dfyxyje = dfyxyje;
    }

    public String getZfsj() {
        return zfsj;
    }

    public void setZfsj(String zfsj) {
        this.zfsj = zfsj;
    }

    public String getZffs() {
        return zffs;
    }

    public void setZffs(String zffs) {
        this.zffs = zffs;
    }

    public String getZfzt() {
        return zfzt;
    }

    public void setZfzt(String zfzt) {
        this.zfzt = zfzt;
    }

    public String getKplx() {
        return kplx;
    }

    public void setKplx(String kplx) {
        this.kplx = kplx;
    }

    public String getSphzt() {
        return sphzt;
    }

    public void setSphzt(String sphzt) {
        this.sphzt = sphzt;
    }

    public String getChs() {
        return chs;
    }

    public void setChs(String chs) {
        this.chs = chs;
    }

    public String getChsfje() {
        return chsfje;
    }

    public void setChsfje(String chsfje) {
        this.chsfje = chsfje;
    }

    public String getSjdhs() {
        return sjdhs;
    }

    public void setSjdhs(String sjdhs) {
        this.sjdhs = sjdhs;
    }

    public String getYfhs() {
        return yfhs;
    }

    public void setYfhs(String yfhs) {
        this.yfhs = yfhs;
    }

    public String getQss() {
        return qss;
    }

    public void setQss(String qss) {
        this.qss = qss;
    }

    public String getThs() {
        return ths;
    }

    public void setThs(String ths) {
        this.ths = ths;
    }

    public String getWfhs() {
        return wfhs;
    }

    public void setWfhs(String wfhs) {
        this.wfhs = wfhs;
    }

    public String getWfhje() {
        return wfhje;
    }

    public void setWfhje(String wfhje) {
        this.wfhje = wfhje;
    }

    public String getFyfs() {
        return fyfs;
    }

    public void setFyfs(String fyfs) {
        this.fyfs = fyfs;
    }

    public String getThrshr() {
        return thrshr;
    }

    public void setThrshr(String thrshr) {
        this.thrshr = thrshr;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getDzbm() {
        return dzbm;
    }

    public void setDzbm(String dzbm) {
        this.dzbm = dzbm;
    }

    public String getShdz() {
        return shdz;
    }

    public void setShdz(String shdz) {
        this.shdz = shdz;
    }

    @Override
    public String toString() {
        return "Zyou{" +
                "ordernmb='" + ordernmb + '\'' +
                ", wbordernmb='" + wbordernmb + '\'' +
                ", bussytype='" + bussytype + '\'' +
                ", orderctm='" + orderctm + '\'' +
                ", salearea='" + salearea + '\'' +
                ", orderstate='" + orderstate + '\'' +
                ", spbm='" + spbm + '\'' +
                ", spmc='" + spmc + '\'' +
                ", ydhsl='" + ydhsl + '\'' +
                ", spdj='" + spdj + '\'' +
                ", sjddze='" + sjddze + '\'' +
                ", yf='" + yf + '\'' +
                ", zfje='" + zfje + '\'' +
                ", syzr='" + syzr + '\'' +
                ", dfyxyje='" + dfyxyje + '\'' +
                ", zfsj='" + zfsj + '\'' +
                ", zffs='" + zffs + '\'' +
                ", zfzt='" + zfzt + '\'' +
                ", kplx='" + kplx + '\'' +
                ", sphzt='" + sphzt + '\'' +
                ", chs='" + chs + '\'' +
                ", chsfje='" + chsfje + '\'' +
                ", sjdhs='" + sjdhs + '\'' +
                ", yfhs='" + yfhs + '\'' +
                ", qss='" + qss + '\'' +
                ", ths='" + ths + '\'' +
                ", wfhs='" + wfhs + '\'' +
                ", wfhje='" + wfhje + '\'' +
                ", fyfs='" + fyfs + '\'' +
                ", thrshr='" + thrshr + '\'' +
                ", cph='" + cph + '\'' +
                ", dzbm='" + dzbm + '\'' +
                ", shdz='" + shdz + '\'' +
                '}';
    }
}
