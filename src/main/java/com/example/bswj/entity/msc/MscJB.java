package com.example.bswj.entity.msc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class MscJB extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String dh;//单号

    @ExcelProperty(index = 1)
    private String mdbm;//门店编码

    @ExcelProperty(index = 2)
    private String ghf;//供货方

    @ExcelProperty(index = 3)
    private String dhrq;//到货日期

    @ExcelProperty(index = 4)
    private String fhdh;//发货单号

    @ExcelProperty(index = 5)
    private String djzt;//单据状态

    @ExcelProperty(index = 6)
    private String cjr;//创建人

    @ExcelProperty(index = 7)
    private String cjsj;//创建时间

    @ExcelProperty(index = 8)
    private String lastUpdateUser;//最后更新人

    @ExcelProperty(index = 9)
    private String lastUpdateDate;//最后更新时间

    @ExcelProperty(index = 10)
    private String memo;//备注

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getMdbm() {
        return mdbm;
    }

    public void setMdbm(String mdbm) {
        this.mdbm = mdbm;
    }

    public String getGhf() {
        return ghf;
    }

    public void setGhf(String ghf) {
        this.ghf = ghf;
    }

    public String getDhrq() {
        return dhrq;
    }

    public void setDhrq(String dhrq) {
        this.dhrq = dhrq;
    }

    public String getFhdh() {
        return fhdh;
    }

    public void setFhdh(String fhdh) {
        this.fhdh = fhdh;
    }

    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "MscJB{" +
                "dh='" + dh + '\'' +
                ", mdbm='" + mdbm + '\'' +
                ", ghf='" + ghf + '\'' +
                ", dhrq='" + dhrq + '\'' +
                ", fhdh='" + fhdh + '\'' +
                ", djzt='" + djzt + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjsj='" + cjsj + '\'' +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
