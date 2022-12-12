package com.example.bswj.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.bswj.entity.xcx.SaleOrderDetails;
import com.example.bswj.entity.xcx.XcxSaParam;
import com.example.bswj.entity.xcxpu.VoucherDetails;
import com.example.bswj.entity.xcxpu.XcxPuParam;
import com.example.bswj.mapper.orderMapper;
import com.example.bswj.saentity.SaleDeliveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MapToJson {

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


    // 这是一个模板，创建销货单的 请求参数 body 的 模板，其他API 可以参考  红单！
    public static String getSAparamsJson(XcxSaParam xcxSaParam,Map<String,Object> userMap){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        sa.put("Code",xcxSaParam.getCode());
        if(userMap == null || userMap.get("departmentCode") == null){
            Map<String,Object> Department = new HashMap<String,Object>();
            Department.put("Code","412");//部门编码
            sa.put("Department",Department);

            Map<String,Object> Clerk = new HashMap<String,Object>();
            Clerk.put("Code","41203");//业务员编码
            sa.put("Clerk",Clerk);
        }else{
            Map<String,Object> Department = new HashMap<String,Object>();
            Department.put("Code",userMap.get("departmentCode").toString());//部门编码
            sa.put("Department",Department);

            Map<String,Object> Clerk = new HashMap<String,Object>();
            Clerk.put("Code",userMap.get("personCode").toString());//业务员编码
            sa.put("Clerk",Clerk);
        }

        String VoucherDate = xcxSaParam.getVoucherDate();// 如果没传，默认就是今天
        if(VoucherDate == null || "".equals(VoucherDate)){
            //单据日期  如果 小程序 不传，就可以 使用 当前日期
            sa.put("VoucherDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }else{
            sa.put("VoucherDate",VoucherDate);//单据日期  如果 小程序 不传，就可以 使用 当前日期
        }
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）
        sa.put("Address",xcxSaParam.getAddress());//送货地址
        sa.put("LinkMan",xcxSaParam.getContacter());//联系人
        sa.put("ContactPhone",xcxSaParam.getContactMobile() );//联系电话


        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code",xcxSaParam.getCustomerCode());//客户编码
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code",xcxSaParam.getCustomerCode());//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);

        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code","16");//业务类型编码，15–普通销售；16–销售退货
        sa.put("BusinessType",BusinessType);
        Map<String,Object> InvoiceType = new HashMap<String,Object>();
        InvoiceType.put("Code","01");//票据类型，枚举类型；00--普通发票，01--专用发票，02–收据；为空时，默认按收据处理
        sa.put("InvoiceType",InvoiceType);

        Map<String,Object> Warehouse = new HashMap<String,Object>();
        Warehouse.put("Code",xcxSaParam.getWarehouseCode());//表头上的 仓库编码
        sa.put("Warehouse",Warehouse);

        Map<String,Object> ReceiveType = new HashMap<String,Object>();
        ReceiveType.put("Code","05");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
        sa.put("ReceiveType",ReceiveType);

        /*Map<String,Object> RdStyle = new HashMap<String,Object>();
        RdStyle.put("Code","201");//出库类别，RdStyleDTO对象，默认为“线上销售”类别； 具体值 我是查的数据库。
        sa.put("RdStyle",RdStyle);*/

        sa.put("Memo","这是 自动同步的销售售后单据！");//备注
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();
        for(int i=0;i<xcxSaParam.getSaleOrderDetails().size();i++){
            SaleOrderDetails sade = xcxSaParam.getSaleOrderDetails().get(i);
            Map<String,Object> DetailM1 = new HashMap<String,Object>();
            Map<String,Object> DetailM1Warehouse = new HashMap<String,Object>();
            DetailM1Warehouse.put("code",xcxSaParam.getWarehouseCode());//明细1 的 仓库编码
            DetailM1.put("Warehouse",DetailM1Warehouse);
            Map<String,Object> DetailM1Inventory = new HashMap<String,Object>();
            DetailM1Inventory.put("code",sade.getInventoryCode());//明细1 的 存货编码
            DetailM1.put("Inventory",DetailM1Inventory);
            Map<String,Object> DetailM1Unit = new HashMap<String,Object>();
            if(sade.getUnitName() == null || "".equals(sade.getUnitName())){
                DetailM1Unit.put("Name","件");//明细1 的 存货计量单位
            }else{
                DetailM1Unit.put("Name",sade.getUnitName());//明细1 的 存货计量单位
            }
            DetailM1.put("Unit",DetailM1Unit);
            //DetailM1.put("Batch","？？？？？？？？？？？？？？？？？？？");//批号
            DetailM1.put("Quantity",sade.getQuantity());//明细1 的 数量
            DetailM1.put("TaxRate",sade.getTaxRate());//明细1 的 税率
            DetailM1.put("OrigTaxPrice",Math.abs(sade.getOrigTaxPrice()));//明细1 的 含税单价(实际上 在传入 来源单据之后，只会用销售订单 上的 单价？？？)

            DetailM1.put("idsourcevouchertype","43");//明细1 的 来源单据类型ID . 销售订单：43 , 销货单：104 ,销售出库单：19
            DetailM1.put("sourceVoucherCode",xcxSaParam.getCode());//明细1 的 来源单据单据编号
            DetailM1.put("sourceVoucherDetailId",sade.getSourceVoucherDetailId());//明细1 的 来源单据单据对应的明细行ID
            SaleDeliveryDetailsList.add(DetailM1);
        }
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

    // 创建 销售订单 STRING
    public static String createSaPUOrderDTO(XcxSaParam xcxSaParam,Map<String,Object> userMap){
        Map<String,Object> dto = new HashMap<String,Object>();

        Map<String,Object> sa = new HashMap<String,Object>();
        sa.put("Code",xcxSaParam.getCode());  //销售订单的编号 ，用的是小程序的

        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code",xcxSaParam.getBusinessType()); // 15--普通销售  16--销售退货"
        sa.put("BusinessType",BusinessType);

        if("16".equals(xcxSaParam.getBusinessType())){
            Map<String,Object> ReturnOrderType = new HashMap<String,Object>();
            ReturnOrderType.put("Code","01"); // 00：仅退款    01：退货退款
            sa.put("ReturnOrderType",ReturnOrderType);
        }

        if(userMap == null || userMap.get("departmentCode") == null){
            Map<String,Object> Department = new HashMap<String,Object>();
            Department.put("Code","412");//部门编码
            sa.put("Department",Department);

            Map<String,Object> Clerk = new HashMap<String,Object>();
            Clerk.put("Code","41203");//业务员编码
            sa.put("Clerk",Clerk);
        }else{
            Map<String,Object> Department = new HashMap<String,Object>();
            Department.put("Code",userMap.get("departmentCode").toString());//部门编码
            sa.put("Department",Department);

            Map<String,Object> Clerk = new HashMap<String,Object>();
            Clerk.put("Code",userMap.get("personCode").toString());//业务员编码
            sa.put("Clerk",Clerk);
        }

        String VoucherDate = xcxSaParam.getVoucherDate();// 如果没传，默认就是今天
        if(VoucherDate == null || "".equals(VoucherDate)){
            //单据日期  如果 小程序 不传，就可以 使用 当前日期
            sa.put("VoucherDate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }else{
            sa.put("VoucherDate",VoucherDate);//单据日期  如果 小程序 不传，就可以 使用 当前日期
        }
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）
        sa.put("Address",xcxSaParam.getAddress());//送货地址
        sa.put("LinkMan",xcxSaParam.getContacter());//联系人
        sa.put("ContactPhone",xcxSaParam.getContactMobile() );//联系电话

        // 如果客户信息没有，就需要 先创建一个客户信息 注意 往来分类 是按 地区来的。
        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code",xcxSaParam.getCustomerCode());//客户编码
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code",xcxSaParam.getCustomerCode());//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);

        /*Map<String,Object> Warehouse = new HashMap<String,Object>();
        Warehouse.put("Code",xcxSaParam.getWarehouseCode());//表头上的 仓库编码
        sa.put("Warehouse",Warehouse);*/

//-----------------------------------------------------------------------------------------------------//
        // 定金对应的 具体的 结算方式。 由于 子母单的存在，子单上没有金额，没有金额就不传 结算方式。且订单是其他
        Float totaltaxamount = 0f;
        Float OrigAmountFlag = xcxSaParam.getSubscriptions().get(0).getOrigAmount();
        if(OrigAmountFlag == null || 0.0f == OrigAmountFlag){
            Map<String,Object> ReciveType = new HashMap<String,Object>();
            ReciveType.put("Code","05");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
            sa.put("ReciveType",ReciveType);// 有可能不是 全额定金哈 ！
        }else{
            Map<String,Object> ReciveType = new HashMap<String,Object>();
            ReciveType.put("Code","01");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
            sa.put("ReciveType",ReciveType);// 有可能不是 全额定金哈 ！

            List<Map<String,Object>> SaleDeliverySettlements = new ArrayList<Map<String,Object>>();
            for(int i=0;i<xcxSaParam.getSubscriptions().size();i++){//此单的 结算方式 明细
                Map<String,Object> settleMap = new HashMap<String,Object>();
                if("16".equals(xcxSaParam.getBusinessType())){
                    settleMap.put("origAmount",-1*xcxSaParam.getSubscriptions().get(i).getOrigAmount() ); //金额
                    totaltaxamount = totaltaxamount + -1*xcxSaParam.getSubscriptions().get(i).getOrigAmount();
                }else{
                    settleMap.put("origAmount",xcxSaParam.getSubscriptions().get(i).getOrigAmount() ); //金额
                    totaltaxamount = totaltaxamount + xcxSaParam.getSubscriptions().get(i).getOrigAmount();
                }

                Map<String,Object> BankAccount = new HashMap<String,Object>();
                String BankAccountName = xcxSaParam.getSubscriptions().get(i).getBankAccount();//账号名称
                if("1627902008".equals(BankAccountName) || "1625792544".equals("BankAccountName")){
                    BankAccount.put("Name","小程序线上账户"); //微信支付下有两个 账号：1627902008、1625792544
                    settleMap.put("BankAccount",BankAccount);
                    //查询次账号在T+中对应的默认结算方式。
                    Map<String,Object> SettleStyle = new HashMap<String,Object>();
                    String settlyStr = xcxSaParam.getSubscriptions().get(i).getSettleStyle();//小程序传入的 支付方式
                    if(settlyStr.contains("微信")){
                        SettleStyle.put("Code","998");
                    }
                    if(settlyStr.contains("支付宝")){
                        SettleStyle.put("Code","999");
                    }
                    if(!settlyStr.contains("微信") && !settlyStr.contains("支付宝")){
                        SettleStyle.put("Code","997");// 写死： 转账
                    }
                    settleMap.put("SettleStyle",SettleStyle);
                }else{
                    BankAccount.put("Name","小程序线下账户");
                    settleMap.put("BankAccount",BankAccount);
                    //查询次账号在T+中对应的默认结算方式。
                    Map<String,Object> SettleStyle = new HashMap<String,Object>();
                    SettleStyle.put("Code","997");// 写死： 转账
                    settleMap.put("SettleStyle",SettleStyle);
                }
                SaleDeliverySettlements.add(settleMap);
            }
            sa.put("Subscriptions",SaleDeliverySettlements);
        }
// -------------------------------------------------------------------------------------------- //
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();

        Float xishu = 1.0f;
        if("16".equals(xcxSaParam.getBusinessType())){
            xishu = -1.0f;
        }
        for(int i=0;i<xcxSaParam.getSaleOrderDetails().size();i++ ){
            Float Quantity = xcxSaParam.getSaleOrderDetails().get(i).getQuantity();
            Float taxamount = xishu*Quantity*Float.valueOf(xcxSaParam.getSaleOrderDetails().get(i).getOrigTaxPrice());
            //totaltaxamount = totaltaxamount + taxamount;

            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> DetailMInventory = new HashMap<String,Object>();
            String inventoryCode = xcxSaParam.getSaleOrderDetails().get(i).getInventoryCode();
            DetailMInventory.put("code",inventoryCode);//明细1 的 存货编码
            DetailM.put("Inventory",DetailMInventory);
            Map<String,Object> DetailMUnit = new HashMap<String,Object>();
            if(xcxSaParam.getSaleOrderDetails().get(i).getUnitName()==null || "".equals(xcxSaParam.getSaleOrderDetails().get(i).getUnitName())){
                DetailMUnit.put("Name","件");// 使用 对应 原始销货单上这个商品的计量单位
            }else{//如果传入的单位和系统的单位一致，就用传入的，否则就用系统的
                String chuanruunit = xcxSaParam.getSaleOrderDetails().get(i).getUnitName();
                String sysunit = xcxSaParam.getSaleOrderDetails().get(i).getSysUnitName();
                if(chuanruunit.equals(sysunit)){
                    DetailMUnit.put("Name",chuanruunit);// 使用 对应 原始销货单上这个商品的计量单位
                }else{
                    DetailMUnit.put("Name",sysunit);// 使用 系统的单位
                }
            }
            DetailM.put("Unit",DetailMUnit);
            DetailM.put("Quantity", Quantity*xishu);

            if(inventoryCode.equals("0502") || inventoryCode.equals("0503") || inventoryCode.equals("0504")){
                //0502 优惠券    0503 红包     0504 订单优惠
                DetailM.put("Quantity", -1*xishu);
            }

            DetailM.put("TaxRate", xcxSaParam.getSaleOrderDetails().get(i).getTaxRate());//明细1 的 税率 , 从 商品里面 获取的，不一定全是 13
            DetailM.put("OrigTaxPrice",Math.abs(xcxSaParam.getSaleOrderDetails().get(i).getOrigTaxPrice()));//明细1 的 含税单价

            if("1".equals(xcxSaParam.getSaleOrderDetails().get(i).getIsPresent())){
                DetailM.put("IsPresent",true);
            }else{
                DetailM.put("IsPresent",false);
            }
            DetailM.put("DetailMemo",xcxSaParam.getSaleOrderDetails().get(i).getMemo());//商品备注

            //DetailM.put("idsourcevouchertype","43");//明细1 的 来源单据类型ID
            //如果要跟 销售订单 关联，则需要传入 下面两个参数。
            //DetailM.put("sourceVoucherCode","SO-2022-03-0006");//明细1 的 来源单据单据编号
            //DetailM.put("sourceVoucherDetailId","9");//明细1 的 来源单据单据对应的明细行ID
            SaleDeliveryDetailsList.add(DetailM);
        }

        if(OrigAmountFlag == null || 0.0f == OrigAmountFlag){

        }else{
            // 如果是 全额 定金 就必须要传 OrigEarnestMoney  ---------------------------------------------------- //
            sa.put("OrigEarnestMoney",totaltaxamount);// 有可能不是 全额定金哈 ！
        }
        sa.put("SaleOrderDetails",SaleDeliveryDetailsList);
        sa.put("Memo",xcxSaParam.getCustomerMemo()+"此单是自动同步的");//整单备注。
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
        rateOfExchangeUnit.put("Name","件"); //计量单位名称
        InvUnitPriceDTOs.put("Unit",rateOfExchangeUnit); // 浮动换算率
        sa.put("InvUnitPriceDTOs",InvUnitPriceDTOs);

        dto.put("dto",sa);
        String json = JSONObject.toJSONString(dto);
        return json;
    }

    public static  String createPUOrderDTO(XcxPuParam xcxPuParam,Map<String,Object> userMap){
        Map<String,Object> dto = new HashMap<String,Object>();
        Map<String,Object> sa = new HashMap<String,Object>();

        Map<String,Object> Department = new HashMap<String,Object>();
        Department.put("Code",userMap.get("departmentCode").toString());//部门编码
        sa.put("Department",Department);

        Map<String,Object> Clerk = new HashMap<String,Object>();
        Clerk.put("Code",userMap.get("personCode").toString());//业务员编码
        sa.put("Clerk",Clerk);

        String VoucherDate = xcxPuParam.getDto().getVoucherDate().toString();
        sa.put("VoucherDate",VoucherDate);//单据日期
        sa.put("ExternalCode",Md5.md5("XJJ"+System.currentTimeMillis()));//外部订单号，不可以重复（MD5，建议记录）

        Map<String,Object> Customer = new HashMap<String,Object>();
        Customer.put("Code",xcxPuParam.getDto().getPartnerCode());//客户编码
        sa.put("Customer",Customer);
        Map<String,Object> SettleCustomer = new HashMap<String,Object>();
        SettleCustomer.put("Code",xcxPuParam.getDto().getPartnerCode());//结算客户编码（一般等同于 客户编码）
        sa.put("SettleCustomer",SettleCustomer);

        Map<String,Object> InvoiceType = new HashMap<String,Object>();
        InvoiceType.put("Code","01");//票据类型，枚举类型；00--普通发票，01--专用发票，02–收据；为空时，默认按收据处理
        sa.put("InvoiceType",InvoiceType);
        Map<String,Object> Warehouse = new HashMap<String,Object>();
        Warehouse.put("Code", xcxPuParam.getDto().getWarehouseCode());//表头上的 仓库编码
        sa.put("Warehouse",Warehouse);
        Map<String,Object> ReciveType = new HashMap<String,Object>();
        ReciveType.put("Code","05");//收款方式，枚举类型；00--限期收款，01--全额订金，02--全额现结，03--月结，04--分期收款，05--其它；
        sa.put("ReciveType",ReciveType);// A账都是现结
        Map<String,Object> RdStyle = new HashMap<String,Object>();
        RdStyle.put("Code","201");//出库类别，RdStyleDTO对象，默认为“线上销售”类别； 具体值 我是查的数据库。  201
        sa.put("RdStyle",RdStyle);
        Map<String,Object> BusinessType = new HashMap<String,Object>();
        BusinessType.put("Code",xcxPuParam.getDto().getBusinessType());//业务类型编码，15–普通销售；16–销售退货
        sa.put("BusinessType",BusinessType);
        //sa.put("Memo","这一单是根据红旗返回的差异自动生成的，请注意区别 ！");//备注
        List<Map<String,Object>> SaleDeliveryDetailsList = new ArrayList<Map<String,Object>>();
        for(VoucherDetails retailmap :xcxPuParam.getDto().getVoucherDetails()){
            Map<String,Object> DetailM = new HashMap<String,Object>();
            Map<String,Object> DetailMWarehouse = new HashMap<String,Object>();
            //明细1 的 仓库编码,这里不好取，但是可以用表头的（因为每一个销货单 只 对应了一个 仓库）
            DetailMWarehouse.put("code",Warehouse.get("Code"));
            DetailM.put("Warehouse",DetailMWarehouse);
            Map<String,Object> DetailMInventory = new HashMap<String,Object>();
            DetailMInventory.put("code",retailmap.getInventoryCode());//明细1 的 存货编码
            DetailM.put("Inventory",DetailMInventory);
            Map<String,Object> DetailMUnit = new HashMap<String,Object>();
            DetailMUnit.put("Name",retailmap.getUnitName());// 使用 对应 原始销货单上这个商品的计量单位
            DetailM.put("Unit",DetailMUnit);
            //DetailM.put("Batch","1");//批号
            DetailM.put("Quantity", retailmap.getQuantity());//返回的差异数量  送货 - 实收 = 差异
            DetailM.put("TaxRate","13");//明细1 的 税率
            DetailM.put("OrigTaxAmount",retailmap.getOrigTaxAmount());//明细1 的 含税单价
            SaleDeliveryDetailsList.add(DetailM);
        }
        sa.put("SaleDeliveryDetails",SaleDeliveryDetailsList);
        dto.put("dto",sa);
        String js = JSONObject.toJSONString(dto);
        System.out.println("进货单JSON ====== " + js);
        return js;
    }

    public static String getPatnerJsonByEntity(XcxSaParam xcxSaParam,Map<String,Object> userMap,String PartnerClassCode){
        String code = xcxSaParam.getContactMobile();
        String name = xcxSaParam.getContacter()+code;
        if(userMap == null || userMap.get("departmentCode") == null){
            userMap = new HashMap<String,Object>();
            userMap.put("departmentCode","412");
            userMap.put("personCode","41203");
        }
        String departmentCode = userMap.get("departmentCode").toString();
        String personCode = userMap.get("personCode").toString();
        String json = "{\n" +
                "        \"dto\": {\n" +
                "                \"Code\":\""+code+"\"," +
                "                \"Name\":\""+name+"\",\n" +
                "                \"PartnerType\":{\"Code\":\"01\"},\n" +
                "                \"PartnerClass\":{\"Code\":\""+PartnerClassCode+"\"},\n" +
                "                \"SaleDepartment\":{\"Code\":\""+departmentCode+"\" },\n" +
                "                \"SaleMan\":{\"Code\":\""+personCode+"\"},\n" +
                "                \"SaleSettleStyle\":{\"Code\":\"05\"}\n" +
                "            }\n" +
                "        }";
        return json;
    }

    public static void main(String[] args) throws Exception{
        String json = "{\"dto\":{\"Address\":\"重庆市重庆市渝中区两路口街道枇杷山正街237号，241号\",\"Customer\":{\"Code\":\"13320244992\"},\"Subscriptions\":[{\"origAmount\":1814.0,\"BankAccount\":{\"Name\":\"小程序线下账户\"},\"SettleStyle\":{\"Code\":\"997\"}}],\"LinkMan\":\"渝亮\",\"OrigEarnestMoney\":1814.0,\"BusinessType\":{\"Code\":\"15\"},\"Code\":\"202212091526066879\",\"ContactPhone\":\"13320244992\",\"Department\":{\"Code\":\"401\"},\"VoucherDate\":\"2022-12-09 15:26:13\",\"Clerk\":{\"Code\":\"32\"},\"SettleCustomer\":{\"Code\":\"13320244992\"},\"ReciveType\":{\"Code\":\"01\"},\"SaleOrderDetails\":[{\"DetailMemo\":\"\",\"TaxRate\":\"0.09\",\"Quantity\":1.0,\"IsPresent\":false,\"Unit\":{\"Name\":\"袋\"},\"OrigTaxPrice\":398.0,\"Inventory\":{\"code\":\"6975508622129\"}},{\"DetailMemo\":\"\",\"TaxRate\":\"0.09\",\"Quantity\":1.0,\"IsPresent\":false,\"Unit\":{\"Name\":\"件\"},\"OrigTaxPrice\":619.0,\"Inventory\":{\"code\":\"6975508620613\"}},{\"DetailMemo\":\"\",\"TaxRate\":\"0.06\",\"Quantity\":1.0,\"IsPresent\":false,\"Unit\":{\"Name\":\"件\"},\"OrigTaxPrice\":299.0,\"Inventory\":{\"code\":\"6975508622259\"}},{\"DetailMemo\":\"\",\"TaxRate\":\"0.09\",\"Quantity\":1.0,\"IsPresent\":false,\"Unit\":{\"Name\":\"袋\"},\"OrigTaxPrice\":498.0,\"Inventory\":{\"code\":\"6975508622150\"}}],\"Memo\":\"此单是自动同步的\",\"ExternalCode\":\"ca3e5a199ae6595082164f5bc114cb2f\"}}";
        String ss = HttpClient.HttpPost(
                "/tplus/api/v2/saleOrder/Create",
                json,
                "3uWZf0mu",
                "F07A56582E5DDBC8F68358940138DBF5",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJpc3YiLCJpc3MiOiJjaGFuamV0IiwidXNlcklkIjoiMzg4NzI2NzkwNjIxNzc3Iiwib3JnSWQiOiIxMjM0NjkwMjY5MDE2MDMwIiwiYWNjZXNzX3Rva2VuIjoiMWRmNWFiZWEtMDM1YS00N2YwLWIyZWQtYjEzOWYzMGU0Mzk4IiwiYXVkIjoiaXN2IiwibmJmIjoxNjcwNzA5NjAzLCJhcHBJZCI6IjU4Iiwic2NvcGUiOiJhdXRoX2FsbCIsImlkIjoiNTI5MDJmMzMtYzE4MS00MTRjLWI0NDEtOGRlOGQyZjYwOTViIiwiZXhwIjoxNjcxMjI4MDAzLCJpYXQiOjE2NzA3MDk2MDMsIm9yZ0FjY291bnQiOiJ1bWpwYXg2bGpob2IifQ.qEJn78VEbISwRIR54nF82D9LawtoSaLn6beL5vkhpWA");

        JSONObject jss = JSONObject.parseObject(ss);
        System.out.println(jss);
        if(jss != null && !"".equals(jss)){
            System.out.println(jss.getString("message"));
        }

    }
}