package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Ptt extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String ptmc;

    @ExcelProperty(index = 1)
    private String tcode;

    @ExcelProperty(index = 2)
    private String tname;

    @ExcelProperty(index = 3)
    private String tdw;


    public Ptt() {
    }

    public Ptt(String ptmc, String tcode, String tname, String tdw) {
        this.ptmc = ptmc;
        this.tcode = tcode;
        this.tname = tname;
        this.tdw = tdw;
    }

    public String getPtmc() {
        return ptmc;
    }

    public void setPtmc(String ptmc) {
        this.ptmc = ptmc;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTdw() {
        return tdw;
    }

    public void setTdw(String tdw) {
        this.tdw = tdw;
    }

    @Override
    public String toString() {
        return "Ptt{" +
                "ptmc='" + ptmc + '\'' +
                ", tcode='" + tcode + '\'' +
                ", tname='" + tname + '\'' +
                ", tdw='" + tdw + '\'' +
                '}';
    }
}
