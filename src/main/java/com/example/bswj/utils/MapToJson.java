package com.example.bswj.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.bswj.saentity.SaleDeliveryDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToJson {

    public static void main(String[] args){
        JSONObject job = JSONObject.parseObject("");
        String lnkshpno = job.getString("lnkshpno");//真实送货单号
        String hndno = job.getString("hndno");//手工单号
        String why = job.getString("brief");//红旗传过来的 弃审 的 原因啊！
        System.out.println(lnkshpno);
        System.out.println(hndno);
        System.out.println(why);
    }

    public static String getXMStrByMap(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> xm = new HashMap<String,Object>();
        xm.put("Code",param.get("code"));
        xm.put("Name",param.get("name"));
        Map<String,Object> ProjectClass = new HashMap<String,Object>();
        ProjectClass.put("Code",param.get("projectclass"));
        xm.put("ProjectClass",ProjectClass);
        dto.put("dto",xm);
        String json = JSONObject.toJSONString(dto);
        return json;
    }


    // 这是一个模板，创建销货单的 请求参数 body 的 模板，其他API 可以参考
    public static String getSAparamsJson(){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();
        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code","HY");//部门编码
        sa.put("Department",Department);
        Map<String,Object> Clerk = new HashMap<String,Object>();
        Clerk.put("Code","CD-030");//业务员编码
        sa.put("Clerk",Clerk);
        sa.put("VoucherDate","2022-03-15");//单据日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）
        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code","CD-030");//客户编码
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code","CD-030");//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);
        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code","15");//业务类型编码，15–普通销售；16–销售退货
        sa.put("BusinessType",BusinessType);
        Map<String,Object> InvoiceType = new HashMap<String,Object>();
        InvoiceType.put("Code","01");//票据类型，枚举类型；00--普通发票，01--专用发票，02–收据；为空时，默认按收据处理
        sa.put("InvoiceType",InvoiceType);
        Map<String,Object> Warehouse = new HashMap<String,Object>();
        Warehouse.put("Code","0101010101");//表头上的 仓库编码
        sa.put("Warehouse",Warehouse);
        Map<String,Object> ReciveType = new HashMap<String,Object>();
        ReciveType.put("Code","76");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
        sa.put("ReciveType",ReciveType);
        Map<String,Object> RdStyle = new HashMap<String,Object>();
        RdStyle.put("Code","201");//出库类别，RdStyleDTO对象，默认为“线上销售”类别； 具体值 我是查的数据库。
        sa.put("RdStyle",RdStyle);
        sa.put("Memo","这是 我的 备注内容，请注意查看9！");//备注
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();
        Map<String,Object> DetailM1 = new HashMap<String,Object>();
        Map<String,Object> DetailM1Warehouse = new HashMap<String,Object>();
        DetailM1Warehouse.put("code","0101010101");//明细1 的 仓库编码
        DetailM1.put("Warehouse",DetailM1Warehouse);
        Map<String,Object> DetailM1Inventory = new HashMap<String,Object>();
        DetailM1Inventory.put("code","HW01-NB-13 S-EMD-W76-YSL");//明细1 的 存货编码
        DetailM1.put("Inventory",DetailM1Inventory);
        Map<String,Object> DetailM1Unit = new HashMap<String,Object>();
        DetailM1Unit.put("Name","台");//明细1 的 存货计量单位
        DetailM1.put("Unit",DetailM1Unit);
        //DetailM1.put("Batch","？？？？？？？？？？？？？？？？？？？");//批号
        DetailM1.put("Quantity","3");//明细1 的 数量
        DetailM1.put("TaxRate","13");//明细1 的 税率
        DetailM1.put("OrigTaxPrice","6666.00");//明细1 的 含税单价(实际上 在传入 来源单据之后，只会用销售订单 上的 单价？？？)

        /*Map<String,Object> SNObject = new HashMap<String,Object>();
        List<Map<String,Object>> SnAccountDetails = new ArrayList<Map<String,Object>>();
        Map<String,Object> snmap1 = new HashMap<String,Object>();
        snmap1.put("SNCode","999006");
        SnAccountDetails.add(snmap1);
        Map<String,Object> snmap2 = new HashMap<String,Object>();
        snmap2.put("SNCode","999004");
        SnAccountDetails.add(snmap2);
        Map<String,Object> snmap3 = new HashMap<String,Object>();
        snmap3.put("SNCode","999001");
        SnAccountDetails.add(snmap3);
        SNObject.put("SnAccountDetails",SnAccountDetails);
        DetailM1.put("SNObject",SNObject);*/

        DetailM1.put("idsourcevouchertype","43");//明细1 的 来源单据类型ID
        DetailM1.put("sourceVoucherCode","SO-2022-03-0006");//明细1 的 来源单据单据编号
        DetailM1.put("sourceVoucherDetailId","9");//明细1 的 来源单据单据对应的明细行ID
        SaleDeliveryDetailsList.add(DetailM1);

        // 表 明细里面的 第二行
        Map<String,Object> DetailM2 = new HashMap<String,Object>();
        Map<String,Object> DetailM2Warehouse = new HashMap<String,Object>();
        DetailM2Warehouse.put("code","0101010101");//明细2 的 仓库编码
        DetailM2.put("Warehouse",DetailM2Warehouse);
        Map<String,Object> DetailM2Inventory = new HashMap<String,Object>();
        DetailM2Inventory.put("code","test1231");//明细2 的 存货编码
        DetailM2.put("Inventory",DetailM2Inventory);
        Map<String,Object> DetailM2Unit = new HashMap<String,Object>();
        DetailM2Unit.put("Name","个");//明细2 的 存货计量单位
        DetailM2.put("Unit",DetailM2Unit);
        //DetailM2.put("Batch","？？？？？？？？？？？？？？？？？？？");//批号
        DetailM2.put("Quantity","3");//明细2 的 数量
        DetailM2.put("TaxRate","13");//明细2 的 税率
        DetailM2.put("OrigTaxAmount","9999.00");//明细2 的 含税金额(实际上 在传入 来源单据之后，只会用销售订单 上的 金额)
        DetailM2.put("idsourcevouchertype","43");//明细2 的 来源单据类型ID
        DetailM2.put("sourceVoucherCode","SO-2022-03-0004");//明细2 的 来源单据单据编号
        DetailM2.put("sourceVoucherDetailId","8");//明细2 的 来源单据单据对应的明细行ID
        SaleDeliveryDetailsList.add(DetailM2);

        sa.put("SaleDeliveryDetails",SaleDeliveryDetailsList);
        dto.put("dto",sa);
        String js = JSONObject.toJSONString(dto);

        return js;
    }

    // 根据 存货 编码  返回 这个存货的 计量单位
    public static String getUnitByCode(String chcode,com.example.bswj.saentity.JsonRootBean sajrb){
        String unit = "个";
        List<SaleDeliveryDetails> listsa = sajrb.getData().getSaleDeliveryDetails();
        for(SaleDeliveryDetails SaleDeliveryDetail : listsa){
            String inventoryCode = SaleDeliveryDetail.getInventory().getCode();
            if(chcode.equals(inventoryCode)){
                unit = SaleDeliveryDetail.getUnit().getName();
            }
            break;
        }
        return unit;
    }

    //参照上面的。把零售单的数据 拼成 一个 销货单的 DTO
    public static String getSAJsonByRetailData(List<Map<String,Object>> dataList,List<Map<String,Object>> settleList){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",dataList.get(0).get("departmentCode"));//部门编码
        sa.put("Department",Department);

        Map<String,Object> Clerk = new HashMap<String,Object>();
        Clerk.put("Code",dataList.get(0).get("personCode"));//业务员编码
        sa.put("Clerk",Clerk);

        String VoucherDate = dataList.get(0).get("VoucherDate").toString();
        sa.put("VoucherDate",VoucherDate);//单据日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）

        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code","LS001");//客户编码  都用 零售客户
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code","LS001");//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);

        Map<String,Object> InvoiceType = new HashMap<String,Object>();
        InvoiceType.put("Code","01");//票据类型，枚举类型；00--普通发票，01--专用发票，02–收据；为空时，默认按收据处理
        sa.put("InvoiceType",InvoiceType);
        Map<String,Object> Warehouse = new HashMap<String,Object>();
        Warehouse.put("Code", dataList.get(0).get("warehouseCode").toString());//表头上的 仓库编码
        sa.put("Warehouse",Warehouse);
        Map<String,Object> ReciveType = new HashMap<String,Object>();
        ReciveType.put("Code","02");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
        sa.put("ReciveType",ReciveType);// A账都是现结
        Map<String,Object> RdStyle = new HashMap<String,Object>();
        RdStyle.put("Code","201");//出库类别，RdStyleDTO对象，默认为“线上销售”类别； 具体值 我是查的数据库。  201
        sa.put("RdStyle",RdStyle);
        Map<String,Object> BusinessType = new HashMap<String,Object>();
        if("34".equals(dataList.get(0).get("idbusitype").toString())){
            BusinessType.put("Code","15");//业务类型编码，15–普通销售；16–销售退货
        }else{
            BusinessType.put("Code","16");//业务类型编码，15–普通销售；16–销售退货
        }
        sa.put("BusinessType",BusinessType);
        //sa.put("Memo","这一单是根据红旗返回的差异自动生成的，请注意区别 ！");//备注
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();
        Float totaltaxamount = 0f;
        for(Map<String,Object> retailmap :dataList){
            String taxamount = retailmap.get("taxamount").toString();
            totaltaxamount = totaltaxamount + Float.valueOf(taxamount);
            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> DetailMWarehouse = new HashMap<String,Object>();
            //明细1 的 仓库编码,这里不好取，但是可以用表头的（因为每一个销货单 只 对应了一个 仓库）
            DetailMWarehouse.put("code",Warehouse.get("Code"));
            DetailM.put("Warehouse",DetailMWarehouse);
            Map<String,Object> DetailMInventory = new HashMap<String,Object>();
            DetailMInventory.put("code",retailmap.get("inventoryCode").toString());//明细1 的 存货编码
            DetailM.put("Inventory",DetailMInventory);
            Map<String,Object> DetailMUnit = new HashMap<String,Object>();
            DetailMUnit.put("Name",retailmap.get("unitName").toString());// 使用 对应 原始销货单上这个商品的计量单位
            DetailM.put("Unit",DetailMUnit);
            //DetailM.put("Batch","？？？？？？？？？？？？？？？？？？？");//批号
            DetailM.put("Quantity", retailmap.get("quantity").toString());//返回的差异数量  送货 - 实收 = 差异
            DetailM.put("TaxRate","13");//明细1 的 税率
            DetailM.put("OrigTaxPrice",retailmap.get("taxprice").toString());//明细1 的 含税单价
            DetailM.put("idsourcevouchertype","43");//明细1 的 来源单据类型ID
            //如果要跟 销售订单 关联，则需要传入 下面两个参数。
            //DetailM.put("sourceVoucherCode","SO-2022-03-0006");//明细1 的 来源单据单据编号
            //DetailM.put("sourceVoucherDetailId","9");//明细1 的 来源单据单据对应的明细行ID
            SaleDeliveryDetailsList.add(DetailM);
        }
        sa.put("OrigSettleAmount",""+totaltaxamount);// 如果选择了全额现结，就必须录入 现结金额
        List<Map<String,Object>> SaleDeliverySettlements = new ArrayList<Map<String,Object>>();
        for(Map<String,Object>  map : settleList){//此单的 结算方式 明细
            Map<String,Object> settleMap = new HashMap<String,Object>();
            settleMap.put("origAmount",map.get("amount").toString()); //金额
            Map<String,Object> SettleStyle = new HashMap<String,Object>();
            Map<String,Object> BankAccount = new HashMap<String,Object>();
            if("996".equals(map.get("code").toString())){ //08上的代金券
                SettleStyle.put("Code","9996");//结算方式编码
                BankAccount.put("Name","代金券");//账号名称
                settleMap.put("SettleStyle",SettleStyle);
                settleMap.put("BankAccount",BankAccount);
                SaleDeliverySettlements.add(settleMap);
            }
            if("94".equals(map.get("code").toString())){ // 08上的 储值卡
                SettleStyle.put("Code","941");//结算方式编码
                BankAccount.put("Name","会员储值");//账号名称
                settleMap.put("SettleStyle",SettleStyle);
                settleMap.put("BankAccount",BankAccount);
                SaleDeliverySettlements.add(settleMap);
            }
            if("99".equals(map.get("code").toString())){ // 08上的 积分抵现
                SettleStyle.put("Code","991");//结算方式编码
                BankAccount.put("Name","积分抵现");//账号名称
                settleMap.put("SettleStyle",SettleStyle);
                settleMap.put("BankAccount",BankAccount);
                SaleDeliverySettlements.add(settleMap);
            }
            if(!"996".equals(map.get("code").toString()) && !"94".equals(map.get("code").toString())
               && !"99".equals(map.get("code").toString())){
                SettleStyle.put("Code",map.get("code").toString());//结算方式编码
                BankAccount.put("Name",map.get("name").toString());//账号名称
                settleMap.put("SettleStyle",SettleStyle);
                settleMap.put("BankAccount",BankAccount);
                SaleDeliverySettlements.add(settleMap);
            }
        }
        sa.put("SaleDeliverySettlements",SaleDeliverySettlements);
        sa.put("SaleDeliveryDetails",SaleDeliveryDetailsList);
        dto.put("dto",sa);
        String js = JSONObject.toJSONString(dto);
        System.out.println("js======" + js);
        return js;
    }
}
