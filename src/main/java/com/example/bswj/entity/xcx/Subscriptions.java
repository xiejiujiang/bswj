/**
  * Copyright 2022 bejson.com 
  */
package com.example.bswj.entity.xcx;

/**
 * Auto-generated: 2022-10-25 10:5:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Subscriptions {

    private String SettleStyle;
    private String BankAccount;
    private Float OrigAmount;

    public String getSettleStyle() {
        return SettleStyle;
    }

    public void setSettleStyle(String settleStyle) {
        SettleStyle = settleStyle;
    }

    public void setBankAccount(String BankAccount) {
        this.BankAccount = BankAccount;
    }
    public String getBankAccount() {
        return BankAccount;
    }

    public void setOrigAmount(Float OrigAmount) {
        this.OrigAmount = OrigAmount;
    }
    public Float getOrigAmount() {
        return OrigAmount;
    }

}