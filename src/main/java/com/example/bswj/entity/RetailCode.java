package com.example.bswj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class RetailCode extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String code;

    public RetailCode() {
    }

    public RetailCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RetailCode{" +
                "code='" + code + '\'' +
                '}';
    }
}
