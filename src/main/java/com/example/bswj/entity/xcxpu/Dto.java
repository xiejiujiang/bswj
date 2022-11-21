/**
  * Copyright 2022 bejson.com 
  */
package com.example.bswj.entity.xcxpu;
import com.example.bswj.entity.xcxpu.VoucherDetails;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2022-10-31 17:44:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Dto {

    private String Code;
    private Date VoucherDate;
    private String ClerkMobile;
    private String ExternalCode;
    private String BusinessType;
    private String PartnerCode;
    private String WarehouseCode;
    private String Memo;
    private List<com.example.bswj.entity.xcxpu.VoucherDetails> VoucherDetails;
    public void setCode(String Code) {
         this.Code = Code;
     }
     public String getCode() {
         return Code;
     }

    public void setVoucherDate(Date VoucherDate) {
         this.VoucherDate = VoucherDate;
     }
     public Date getVoucherDate() {
         return VoucherDate;
     }

    public void setExternalCode(String ExternalCode) {
         this.ExternalCode = ExternalCode;
     }
     public String getExternalCode() {
         return ExternalCode;
     }

    public void setBusinessType(String BusinessType) {
         this.BusinessType = BusinessType;
     }
     public String getBusinessType() {
         return BusinessType;
     }

    public void setPartnerCode(String PartnerCode) {
         this.PartnerCode = PartnerCode;
     }
     public String getPartnerCode() {
         return PartnerCode;
     }

    public void setWarehouseCode(String WarehouseCode) {
         this.WarehouseCode = WarehouseCode;
     }
     public String getWarehouseCode() {
         return WarehouseCode;
     }

    public void setMemo(String Memo) {
         this.Memo = Memo;
     }
     public String getMemo() {
         return Memo;
     }

    public void setVoucherDetails(List<VoucherDetails> VoucherDetails) {
         this.VoucherDetails = VoucherDetails;
     }
     public List<VoucherDetails> getVoucherDetails() {
         return VoucherDetails;
     }

    public String getClerkMobile() {
        return ClerkMobile;
    }

    public void setClerkMobile(String clerkMobile) {
        ClerkMobile = clerkMobile;
    }
}