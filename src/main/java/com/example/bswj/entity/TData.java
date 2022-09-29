package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class TData extends BaseRowModel {

    @ExcelProperty(value = "*单据日期",index = 0)
    private String voucherDate;

    @ExcelProperty(value = "*单据编号",index = 1)
    private String code;

    @ExcelProperty(value = "*供应商编码",index = 2)
    private String partner;

    @ExcelProperty(value = "供应商",index = 3)
    private String partnerName;

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
}
