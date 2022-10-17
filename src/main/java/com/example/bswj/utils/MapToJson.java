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

    // 通过 MAP 来创建 销售订单 STRING
    public static String createSaPUOrderDTO(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();

        Map<String,Object> sa = new HashMap<String,Object>();
        //sa.put("Code",param.get("code"));  销售订单的编号 ，由系统自己创建
        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code",param.get("projectclass")); // 15--普通销售  16--销售退货"
        sa.put("BusinessType",BusinessType);

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",param.get("departmentCode"));//部门编码
        sa.put("Department",Department);

        Map<String,Object> Clerk = new HashMap<String,Object>();
        Clerk.put("Code",param.get("personCode"));//业务员编码
        sa.put("Clerk",Clerk);

        String VoucherDate = param.get("VoucherDate").toString();
        sa.put("VoucherDate",VoucherDate);//单据日期  如果 小程序 不传，就可以 使用 当前日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）

        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code",param.get("CustomerCode"));//客户编码  都用 零售客户
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code",param.get("SettleCustomerCode"));//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);

        Map<String,Object> ReciveType = new HashMap<String,Object>();
        ReciveType.put("Code","01");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
        sa.put("ReciveType",ReciveType);// 有可能不是 全额定金哈 ！
//-----------------------------------------------------------------------------------------------------//
        // 定金对应的 具体的 结算方式
        List<Map<String,Object>> SaleDeliverySettlements = new ArrayList<Map<String,Object>>();
        /*for(Map<String,Object>  map : settleList){//此单的 结算方式 明细
            Map<String,Object> settleMap = new HashMap<String,Object>();
            settleMap.put("origAmount",map.get("amount").toString()); //金额
            Map<String,Object> SettleStyle = new HashMap<String,Object>();
            Map<String,Object> BankAccount = new HashMap<String,Object>();
            SettleStyle.put("Code","9996");//结算方式编码
            BankAccount.put("Name","代金券");//账号名称
            settleMap.put("SettleStyle",SettleStyle);
            settleMap.put("BankAccount",BankAccount);
            SaleDeliverySettlements.add(settleMap);
        }*/
        sa.put("Subscriptions",SaleDeliverySettlements);
// -------------------------------------------------------------------------------------------- //
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();
        Float totaltaxamount = 0f;
        /*for(Map<String,Object> retailmap : dataList){
            String taxamount = retailmap.get("taxamount").toString();
            totaltaxamount = totaltaxamount + Float.valueOf(taxamount);
            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> DetailMInventory = new HashMap<String,Object>();
            DetailMInventory.put("code",retailmap.get("inventoryCode").toString());//明细1 的 存货编码
            DetailM.put("Inventory",DetailMInventory);
            Map<String,Object> DetailMUnit = new HashMap<String,Object>();
            DetailMUnit.put("Name",retailmap.get("unitName").toString());// 使用 对应 原始销货单上这个商品的计量单位
            DetailM.put("Unit",DetailMUnit);
            //DetailM.put("Batch","？？？？？？？？？？？？？？？？？？？");//批号
            DetailM.put("Quantity", retailmap.get("quantity").toString());//返回的差异数量  送货 - 实收 = 差异
            DetailM.put("TaxRate",???);//明细1 的 税率 , 从 商品里面 获取的，不一定全是 13
            DetailM.put("OrigTaxPrice",retailmap.get("taxprice").toString());//明细1 的 含税单价
            DetailM.put("idsourcevouchertype","43");//明细1 的 来源单据类型ID
            //如果要跟 销售订单 关联，则需要传入 下面两个参数。
            //DetailM.put("sourceVoucherCode","SO-2022-03-0006");//明细1 的 来源单据单据编号
            //DetailM.put("sourceVoucherDetailId","9");//明细1 的 来源单据单据对应的明细行ID
            SaleDeliveryDetailsList.add(DetailM);
        }*/
        // 如果是 全额 定金 就必须要传 OrigEarnestMoney  ---------------------------------------------------- //
        sa.put("OrigEarnestMoney",totaltaxamount);// 有可能不是 全额定金哈 ！
        sa.put("SaleOrderDetails",SaleDeliveryDetailsList);
        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    //创建费用单
    public static String createExpenseDTO(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",param.get("departmentCode"));//部门编码
        sa.put("Department",Department);

        Map<String,Object> Clerk = new HashMap<String,Object>();
        Clerk.put("Code",param.get("personCode"));//业务员编码
        sa.put("Clerk",Clerk);

        String VoucherDate = param.get("VoucherDate").toString();
        sa.put("VoucherDate",VoucherDate);//单据日期  如果 小程序 不传，就可以 使用 当前日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）

        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code",param.get("projectclass")); //   37 往来费用 38 现金费用
        sa.put("BusinessType",BusinessType);

        Map<String,Object> ARAPAccountDirection = new HashMap<String,Object>();
        ARAPAccountDirection.put("Code",""); //  00 应收，01 应付
        sa.put("ARAPAccountDirection",ARAPAccountDirection);

        Map<String,Object> billType = new HashMap<String,Object>();
        billType.put("Code",""); //   00:运费发票 , 01:专用发票 , 02:收据，03:普通发票-内含, 04:普通发票
        sa.put("billType",billType);

        Map<String,Object> Partner = new HashMap<String,Object>();
        Partner.put("Code",""); //   BusinessType为37为必填，38时为非必填；T+系统中存在的往来单位，其中的code,表示该往来单位的编码
        sa.put("Partner",Partner);

        List<Map<String,Object>> Details = new ArrayList<Map<String,Object>>();
        /*for(Map<String,Object> retailmap :dataList){//传入的费用明细？
            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> expenseitem = new HashMap<String,Object>();
            expenseitem.put("code",retailmap.get("code").toString());//明细1 的 费用编码
            DetailM.put("expenseitem",expenseitem);
            DetailM.put("TaxRate",???);// 这个费用单 的税率？
            DetailM.put("OrigTaxAmount",retailmap.get("OrigTaxAmount").toString());//明细1 的 费用金额
            DetailM.put("expenseItemUse",???);// 这个费用1的 用途
            Details.add(DetailM);
        }*/
        sa.put("Details",Details);
        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    //创建 收入单
    public static String createIncomeDTO(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",param.get("departmentCode"));//部门编码
        sa.put("Department",Department);

        Map<String,Object> Person = new HashMap<String,Object>();
        Person.put("Code",param.get("personCode"));//业务员编码
        sa.put("Person",Person);

        String VoucherDate = param.get("VoucherDate").toString();
        sa.put("VoucherDate",VoucherDate);//单据日期  如果 小程序 不传，就可以 使用 当前日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）

        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code",param.get("projectclass")); //   T+系统中存在的业务类型，其中code表示该业务类型的编码
        sa.put("BusinessType",BusinessType);

        Map<String,Object> billType = new HashMap<String,Object>();
        billType.put("Code",""); //   00:运费发票 , 01:专用发票 , 02:收据，03:普通发票-内含, 04:普通发票
        sa.put("billType",billType);

        Map<String,Object> Partner = new HashMap<String,Object>();
        Partner.put("Code",""); //   BusinessType为37为必填，38时为非必填；T+系统中存在的往来单位，其中的code,表示该往来单位的编码
        sa.put("Partner",Partner);

        List<Map<String,Object>> Details = new ArrayList<Map<String,Object>>();
        /*for(Map<String,Object> retailmap : dataList){//传入的收入单 明细？
            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> incomeitem = new HashMap<String,Object>();
            incomeitem.put("code",retailmap.get("code").toString());//明细1 的 收入 编码
            DetailM.put("incomeitem",incomeitem);
            DetailM.put("taxExchange",???);// 这个收入 的税率？
            DetailM.put("OrigTaxAmount",retailmap.get("OrigTaxAmount").toString());//明细1 的 含税金额
            Details.add(DetailM);
        }*/
        sa.put("Details",Details);

        // 定金对应的 具体的 结算方式
        List<Map<String,Object>> MultiSettles = new ArrayList<Map<String,Object>>();
        /*for(Map<String,Object>  map : settleList){//此单的 结算方式 明细
            Map<String,Object> settleMap = new HashMap<String,Object>();
            settleMap.put("OrigAmount",map.get("amount").toString()); //金额
            Map<String,Object> SettleStyle = new HashMap<String,Object>();
            Map<String,Object> BankAccount = new HashMap<String,Object>();
            SettleStyle.put("Code","");//结算方式编码
            BankAccount.put("Name","");//账号名称
            settleMap.put("SettleStyle",SettleStyle);
            settleMap.put("BankAccount",BankAccount);
            MultiSettles.add(settleMap);
        }*/
        sa.put("MultiSettles",MultiSettles);

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }


    //创建 往来单位
    public static String createWLDWDTO(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        sa.put("Code",param.get("Code"));//往来单位的编码
        sa.put("Name",param.get("Name"));//往来单位的名称

        //sa.put("SaleCheckMonth","");//客户设置--默认收款方式--月结-
        //sa.put("PurchaseStartDate","");//供应商设置--默认付款方式--月结--账期起始日

        //01 客户 ,00 供应商 ,02 客户/供应商, 为空时，默认为客户
        Map<String,Object> PartnerType = new HashMap<String,Object>();
        PartnerType.put("Code",param.get("PartnerTypeCode"));
        sa.put("PartnerType",PartnerType);

        //默认收款方式编码  00：限期收款   01：全额订金    02：全额现结    03：月结    04：分期收款   05：其它
        Map<String,Object> SaleSettleStyle = new HashMap<String,Object>();
        SaleSettleStyle.put("Code",param.get("SaleSettleStyleCode"));
        sa.put("SaleSettleStyle",SaleSettleStyle);

        Map<String,Object> SaleDepartment = new HashMap<String,Object>();
        SaleDepartment.put("Code",param.get("SaleDepartment"));//分管部门
        sa.put("SaleDepartment",SaleDepartment);

        Map<String,Object> PartnerClass = new HashMap<String,Object>();
        PartnerClass.put("Code",param.get("PartnerClassCode"));//所属分类编码
        sa.put("PartnerClass",PartnerClass);

        /*Map<String,Object> District = new HashMap<String,Object>();
        District.put("Code",param.get("DistrictCode"));//所属地区
        sa.put("District",District);*/

        Map<String,Object> DefaultInvoiceTypeOfPayment = new HashMap<String,Object>();
        //票据类型编码00：专用发票01：普通发票-内含03：农副产品发票04：红字专用发票05：红字普通发票-内含07：红字农副产品发票08：收据09：普通发票10：红字普通发票
        DefaultInvoiceTypeOfPayment.put("Code",param.get(""));
        sa.put("DefaultInvoiceTypeOfPayment",DefaultInvoiceTypeOfPayment);

        //结算客户，当性质为客户或客户/供应商时有效 当企业的销售对象与结算对象一样时，可空，默认与往来单位编码一样 当企业的销售对象与结算对象不一样时，指定对应结算客户的编码
        /*Map<String,Object> SettlementPartner = new HashMap<String,Object>();
        SettlementPartner.put("Code",param.get(""));
        sa.put("SettlementPartner",SettlementPartner);*/

        Map<String,Object> TaxRate = new HashMap<String,Object>();
        TaxRate.put("Name",param.get(""));//税率
        sa.put("TaxRate",TaxRate);

        /*Map<String,Object> MemberType = new HashMap<String,Object>();
        MemberType.put("Code",param.get(""));//会员类型
        sa.put("MemberType",MemberType);*/

        Map<String,Object> DefaultInvoiceTypeOfReceive = new HashMap<String,Object>();
        DefaultInvoiceTypeOfReceive.put("Code",param.get(""));//票据类型编码00：普通发票01：专用发票02：收据
        sa.put("DefaultInvoiceTypeOfReceive",DefaultInvoiceTypeOfReceive);

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    //创建 部门
    public static String createDepartment(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Parent = new HashMap<String,Object>();
        Parent.put("Code",param.get("parentCode"));//上级部门 编码
        sa.put("Parent",Parent);
        sa.put("Code",param.get("Code"));
        sa.put("Name",param.get("Name"));

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    //创建 员工
    public static String createUser(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",param.get("departmentCode"));//部门 编码
        sa.put("Department",Department);

        /*Map<String,Object> Position = new HashMap<String,Object>();
        Position.put("Id",param.get(""));// 职务ID
        Position.put("Name",param.get(""));// 职务名称
        sa.put("Position",Position);*/

        sa.put("Code",param.get("Code"));
        sa.put("Name",param.get("Name"));
        sa.put("MobilePhoneNo",param.get("MobilePhoneNo"));//员工手机号
        sa.put("IsSalesMan","True");//是否业务员

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    //创建 商品存货
    public static String createInventory(Map<String,String> param){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        sa.put("Code",param.get("code"));//存货编码
        sa.put("Name",param.get("name"));//存货名称

        Map<String,Object> Unit = new HashMap<String,Object>();
        Unit.put("Code",param.get("Unitcode"));// 主单位编码
        Unit.put("Name",param.get("UnitName"));// 主单位名称
        sa.put("Unit",Unit);

        Map<String,Object> InventoryClass = new HashMap<String,Object>();
        InventoryClass.put("Code",param.get("InventoryClassCode"));// 存货分类编码
        InventoryClass.put("Name",param.get("InventoryClassName"));// 存货分类名称
        sa.put("InventoryClass",InventoryClass);

        sa.put("IsSingleUnit","false");// 是否单计量 false / true
        sa.put("UnitType","01");// 计量单位类型\"00\":单计量\"01\":多计量
        sa.put("IsPurchase","True");//存货属性：外购属性 取值："True","False"
        sa.put("IsSale","True");//存货属性：销售属性 取值："True","False"
        sa.put("IsLaborCost","False");//存货属性：劳务属性 取值："True","False"
        sa.put("IsNew","True");//是否新品
        sa.put("IsSuite","False");// 存货属性：成套件属性取值："True","False"
        sa.put("IsBatch","True");// 是否开启批号管理
        sa.put("IsQualityPeriod","True");// 是否开启有效期管理（开启的前提是开启批号管理）
        sa.put("Specification",param.get("Specification"));//规格型号
        sa.put("InventoryDescript",param.get("InventoryDescript"));//存货描述

        Map<String,Object> ValueType = new HashMap<String,Object>();
        ValueType.put("Code",param.get("03"));//  计价方式编码\"00\" : 全月平均\"01\" : 移动平均\"03\" : 先进先出\"04\" : 个别计价
        sa.put("ValueType",ValueType);

        Map<String,Object> UnitGroup = new HashMap<String,Object>();
        UnitGroup.put("Name",param.get("UnitGroupName"));// 多计量单位组名称
        sa.put("UnitGroup",UnitGroup);

        Map<String,Object> UnitBySale = new HashMap<String,Object>();
        UnitBySale.put("Name",param.get("UnitBySale"));// 销售常用单位；示例{Name:"盒"}
        sa.put("UnitBySale",UnitBySale);

        Map<String,Object> UnitByRetail = new HashMap<String,Object>();
        UnitByRetail.put("Name",param.get("UnitByRetail"));// 零售常用单位；{Name:"盒"}
        sa.put("UnitByRetail",UnitByRetail);

        Map<String,Object> UnitByPurchase = new HashMap<String,Object>();
        UnitByPurchase.put("Name",param.get("UnitByPurchase"));// 采购常用单位；{Name:"盒"}
        sa.put("UnitByPurchase",UnitByPurchase);

        Map<String,Object> UnitByStock = new HashMap<String,Object>();
        UnitByStock.put("Name",param.get("UnitByStock"));// 库存常用单位；{Name:"盒"}
        sa.put("UnitByStock",UnitByStock);

        Map<String,Object> UnitByManufacture = new HashMap<String,Object>();
        UnitByManufacture.put("Name",param.get("UnitByManufacture"));// 生产常用单位；{Name:"盒"}
        sa.put("UnitByManufacture",UnitByManufacture);

        Map<String,Object> ExpiredUnit = new HashMap<String,Object>();
        ExpiredUnit.put("Id",param.get("ExpiredUnit")); // 保质期单位枚举ID  EXP:  2157：天  2158：月
        sa.put("ExpiredUnit",ExpiredUnit); //保质期单位；如果要传这个参数，必须同时传参开启有效期管理（IsQualityPeriod:true）

        // 浮动换算信息
        Map<String,Object> InvUnitPriceDTOs = new HashMap<String,Object>();
        InvUnitPriceDTOs.put("rateOfExchange",param.get("rateOfExchange"));// 浮动换算率
        Map<String,Object> rateOfExchangeUnit = new HashMap<String,Object>();
        rateOfExchangeUnit.put("Name","?????"); //计量单位名称
        InvUnitPriceDTOs.put("Unit",rateOfExchangeUnit); // 浮动换算率
        sa.put("InvUnitPriceDTOs",InvUnitPriceDTOs);

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }
}