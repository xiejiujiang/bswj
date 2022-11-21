package com.example.bswj.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bswj.SAsubscribe.SACsubJsonRootBean;
import com.example.bswj.entity.xcx.XcxSaParam;
import com.example.bswj.entity.xcxpu.XcxPuParam;
import com.example.bswj.mapper.orderMapper;
import com.example.bswj.saentity.JsonRootBean;
import com.example.bswj.service.BasicService;
import com.example.bswj.utils.*;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BasicServiceImpl implements BasicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);

    @Autowired
    private orderMapper orderMapper;

    @Override
    public String createXM(Map<String, String> params) {
        String result = "";
        try {
            String json = MapToJson.getXMStrByMap(params);//这个参数只能 单独来 设置 生成。
            result = HttpClient.HttpPost("/tplus/api/v2/Project/Create",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // result:"31" 成功！
    }


    @Override
    public String getXM(Map<String, String> params) {
        String result = "";
        try {
            String json = "{ param:{\"Code\":\"111\"} }";
            result = HttpClient.HttpPost("/tplus/api/v2/Project/Query2",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSONObject.parseArray(result);
        List<Map> xmList = jsonArray.toJavaList(Map.class);
        return result;
    }


    @Override
    public String createXMClass(Map<String, String> params) {
        String result = "";
        try {
            String json = "{dto:{\"Code\":\"09002\", \"Name\":\"销售二部\", \"Parent\":{\"Code\":\"09\"}}}";
            result = HttpClient.HttpPost("/tplus/api/v2/ProjectClass/Create",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;// result:null
    }

    @Override
    public String getXMClass(Map<String, String> params) {
        String result = "";
        try {
            String json = "{param:{Code:\"09\"}}"; //Name 也可以
            result = HttpClient.HttpPost("/tplus/api/v2/ProjectClass/Query",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;// result:null
    }

    @Override
    public String getWarehouse(Map<String, String> params) {
        String result = "";
        try {
            String json = "{param:{Code:\"09\"}}"; //Name 也可以，  {param:{}} 查所有
            result = HttpClient.HttpPost("/tplus/api/v2/warehouse/Query",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;// result:null
    }

    @Override
    public String createWarehouse(Map<String, String> params) {
        String result = "";
        try {
            //16.0 最简传参，若非 就不传 warehouseType 01：普通仓02：现场仓03：委外仓,其他参数请参照API文档
            String json = "{\"dto\": {\"Code\": \"001\",\"Name\": \"电子设备库\",\"warehouseType\": {\"Code\": \"01\"}}}";
            result = HttpClient.HttpPost("/tplus/api/v2/warehouse/Create",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;// result:"3"     其中3代表新创建的仓库ID
    }

    @Override
    public String createPZ(Map<String, String> params) {
        String result = "";
        try {
            Map<String, Object> dto = new HashMap<String, Object>();
            Map<String, Object> sa = new HashMap<String, Object>();
            sa.put("ExternalCode", Md5.md5("xjj" + System.currentTimeMillis()));
            Map<String, Object> DocType = new HashMap<String, Object>();
            DocType.put("Code", "记");
            DocType.put("Name", "记");
            sa.put("VoucherDate", "2022-03-22");
            //sa.put("AttachedVoucherNum",0); //附件数量
            sa.put("Memo", "凭证的备注！！！！！！！！");
            List<Map<String, Object>> Entrys = new ArrayList<Map<String, Object>>();

            Map<String, Object> en1 = new HashMap<String, Object>();//分录1
            en1.put("Summary", "提现");//摘要
            Map<String, Object> Account = new HashMap<String, Object>();
            Account.put("Code", "112204");//科目  只能末级 ，应收账款
            en1.put("Account", Account);
            Map<String, Object> Currency = new HashMap<String, Object>();
            Currency.put("Code", "RMB");//货币编码  如果没有开启币种管理，就是默认 RMB
            en1.put("Currency", Currency);
            en1.put("AmountCr", 999);//贷方
            //en1.put("AmountDr",0);//借方
            List<Map<String, Object>> AuxInfos = new ArrayList<Map<String, Object>>();
            Map<String, Object> fzx = new HashMap<String, Object>();

            Map<String, Object> AuxAccDepartmentMap = new HashMap<String, Object>();
            AuxAccDepartmentMap.put("Code", "999");
            fzx.put("AuxAccDepartment", AuxAccDepartmentMap);

            Map<String, Object> AuxAccPersonMap = new HashMap<String, Object>();
            AuxAccPersonMap.put("Code", "222");
            fzx.put("AuxAccPerson", AuxAccPersonMap);

            Map<String, Object> AuxAccCustomerMap = new HashMap<String, Object>();
            AuxAccCustomerMap.put("Code", "0003001");
            fzx.put("AuxAccCustomer", AuxAccCustomerMap);

            AuxInfos.add(fzx);
            en1.put("AuxInfos", AuxInfos);//辅助项
            Entrys.add(en1);


            Map<String, Object> en2 = new HashMap<String, Object>();//分录2
            en2.put("Summary", "提现");//摘要
            Map<String, Object> Account2 = new HashMap<String, Object>();
            Account2.put("Code", "100102");//科目  只能末级 ，应收账款
            en2.put("Account", Account2);
            Map<String, Object> Currency2 = new HashMap<String, Object>();
            Currency2.put("Code", "RMB");//货币编码  如果没有开启币种管理，就是默认 RMB
            en2.put("Currency", Currency2);
            //en2.put("AmountCr",0);//贷方
            en2.put("AmountDr", 999);//借方
            List<Map<String, Object>> AuxInfos2 = new ArrayList<Map<String, Object>>();
            Map<String, Object> AuxAccDepartment2 = new HashMap<String, Object>();//部门辅助项
            Map<String, Object> map4 = new HashMap<String, Object>();
            map4.put("Code", "999");//部门辅助项
            AuxAccDepartment2.put("AuxAccDepartment", map4);//部门辅助项
            AuxInfos2.add(AuxAccDepartment2);//部门辅助项
            en2.put("AuxInfos", AuxInfos2);//辅助项
            Entrys.add(en2);

            sa.put("Entrys", Entrys);
            sa.put("DocType", DocType);
            dto.put("dto", sa);
            String json = JSONObject.toJSONString(dto);
            result = HttpClient.HttpPost("/tplus/api/v2/doc/ReturnCodeCreate",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    orderMapper.getTokenByAppKey(params.get("AppKey")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JsonRootBean getSaOrder(Map<String, String> params) {
        JsonRootBean jrb = new JsonRootBean();
        String code = params.get("code");//单据编号
        String token = orderMapper.getTokenByAppKey(params.get("AppKey"));
        try {
            String json = "{param:{voucherCode:\"" + code + "\"}}"; //{param:{}} 查所有
            String result = HttpClient.HttpPost("/tplus/api/v2/SaleDeliveryOpenApi/GetVoucherDTO",
                    json,
                    params.get("AppKey"),
                    params.get("AppSecret"),
                    token);
            JSONObject job = JSONObject.parseObject(result);
            jrb = job.toJavaObject(JsonRootBean.class);
            if(jrb  == null || jrb.getCode() == null || jrb.getCode().contains("999")){//说明请求畅捷通失败，就再来一次
                String result2 = HttpClient.HttpPost("/tplus/api/v2/SaleDeliveryOpenApi/GetVoucherDTO",
                        json,
                        params.get("AppKey"),
                        params.get("AppSecret"),
                        token);
                JSONObject job2 = JSONObject.parseObject(result2);
                jrb = job2.toJavaObject(JsonRootBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jrb;
    }


    //根据单据编号 获取 附件 ID
    public List<Map<String, String>> getfjidByCode(String code) {
        List<Map<String, String>> ids = orderMapper.getfjidByCode(code);
        return ids;
    }


    public String unAuditZDorder(String voucherCode){
        String result = "";
        try{
            String auditjson = "{\n" +
                    "  \"param\": {\n" +
                    "    \"voucherCode\": \""+voucherCode+"\"\n" +
                    "  }\n" +
                    "}";
            String access_token = orderMapper.getTokenByAppKey("3uWZf0mu");//appKey
            String auditResult = HttpClient.HttpPost("/tplus/api/v2/SaleDeliveryOpenApi/UnAudit",auditjson,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    access_token);
            JSONObject unauditjob = JSONObject.parseObject(auditResult);
            if(unauditjob.getString("code").contains("999")){//如果弃审失败，就再弃审一次 试试
                auditResult = HttpClient.HttpPost("/tplus/api/v2/SaleDeliveryOpenApi/UnAudit",auditjson,
                        "3uWZf0mu",
                        "F07A56582E5DDBC8F68358940138DBF5",
                        access_token);
            }
            LOGGER.info("-------------- 单号： " + voucherCode + "的弃审结果是：" + auditResult);
            result = auditResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String createSaPuOrder(XcxSaParam xcxSaParam, String token) {
        String result = "{ \"result\":\"success\" }";
        try {
            //关键是传入的这个 map 必须要包含 销售订单 需要的所有内容
            Map<String,Object> userMap = orderMapper.getUserInfoByMobile(xcxSaParam.getClerkMobile());

            //处理这个往来单位的 分类的 code
            String address = xcxSaParam.getAddress();//黑龙江省鸡西市鸡东县申达小区A坐5号门市麻小旋小火锅
            String PartnerClassCode = "QT";//这个往来单位 所属的末级 分类 编码
            String shi = "";//市
            if(address.indexOf("省") != -1){ //有 “省”
                if(address.indexOf("市") != -1){//有 “市”
                    shi = address.substring(address.indexOf("省")+1,address.indexOf("市")); //鸡西
                    PartnerClassCode = orderMapper.getPartnerClassCodeBySHI(shi);
                }
            }else{
                if(address.indexOf("市") != -1){//有 “市”
                    shi = address.substring(0,address.indexOf("市")); //鸡西
                    PartnerClassCode = orderMapper.getPartnerClassCodeBySHI(shi);
                }
            }
            if(PartnerClassCode == null || "".equals(PartnerClassCode)){
                PartnerClassCode = "QT";
            }

            //先判断这个往来单位有没有哦
            String customerCode = xcxSaParam.getCustomerCode();//小程序传入的code
            String customerResult = "1";
            if(orderMapper.getCustomerCount(customerCode) == 0){
                String customerjson = MapToJson.getPatnerJsonByEntity(xcxSaParam,userMap,PartnerClassCode);
                LOGGER.info("--- 访问创建客户API json == " + customerjson);
                customerResult = HttpClient.HttpPost(
                        "/tplus/api/v2/partner/Create",
                        customerjson,
                        "3uWZf0mu",
                        "F07A56582E5DDBC8F68358940138DBF5",
                        token);
            }
            if("15".equals(xcxSaParam.getBusinessType())){
                if(Integer.valueOf(customerResult.replaceAll("\"","")) > 0 ){//说明 请求 创建往来单位API 成功了！
                    //再设置税率
                    for(int i=0;i<xcxSaParam.getSaleOrderDetails().size();i++ ){
                        String inventoryCode = xcxSaParam.getSaleOrderDetails().get(i).getInventoryCode();
                        String taxRate = orderMapper.getInvetoryTaxRateByCode(inventoryCode);//从存货档案种 获取对应的税率
                        taxRate = "" + ( Float.valueOf(taxRate) / 100f );
                        xcxSaParam.getSaleOrderDetails().get(i).setTaxRate(taxRate);
                    }

                    //销售订单的JSON
                    String json = MapToJson.createSaPUOrderDTO(xcxSaParam,userMap);
                    LOGGER.info("调用T+ 创建销售订单API的json == " + json);
                    HttpClient.HttpPost(
                            "/tplus/api/v2/saleOrder/Create",
                            json,
                            "3uWZf0mu",
                            "F07A56582E5DDBC8F68358940138DBF5",
                            token);
                }
            }else{
                // 退货：售后的单据，都是走 红字的销货单。要关联之前的销售订单。
                for(int i=0;i<xcxSaParam.getSaleOrderDetails().size();i++ ){
                    String inventoryCode = xcxSaParam.getSaleOrderDetails().get(i).getInventoryCode();
                    String taxRate = orderMapper.getInvetoryTaxRateByCode(inventoryCode);//从存货档案种 获取对应的税率
                    taxRate = "" + ( Float.valueOf(taxRate) / 100f );
                    xcxSaParam.getSaleOrderDetails().get(i).setTaxRate(taxRate);
                    //明细1 的 来源单据单据对应的明细行ID
                    String sourceVoucherDetailId = orderMapper.getSourceVoucherDetailIdBy(inventoryCode,xcxSaParam.getCode());
                    xcxSaParam.getSaleOrderDetails().get(i).setSourceVoucherDetailId(sourceVoucherDetailId);
                }
                if(orderMapper.getSapuOrderAfterList(xcxSaParam.getCode()) == 0){
                    //此订单没有后续单据，只有销售订单，则创建红字的销售订单
                    xcxSaParam.setCode(xcxSaParam.getCode()+"-1");
                    String json = MapToJson.createSaPUOrderDTO(xcxSaParam,userMap);
                    LOGGER.info("调用T+ 创建 红字的销售订单API的json == " + json);
                    HttpClient.HttpPost(
                            "/tplus/api/v2/saleOrder/Create",
                            json,
                            "3uWZf0mu",
                            "F07A56582E5DDBC8F68358940138DBF5",
                            token);
                }else{
                    //红字的 销货单 JSON  ( 取当前 最大的尾号 + 1.再 加  横   )
                    String dqcode = orderMapper.getDQCODEBY(xcxSaParam.getCode());//查询销货单里面有没有当前这个code的编号
                    xcxSaParam.setCode(dqcode+"-1");
                    String json = MapToJson.getSAparamsJson(xcxSaParam,userMap);
                    LOGGER.info("调用T+ 创建 红字的 销货单  API的json == " + json);
                    HttpClient.HttpPost(
                            "/tplus/api/v2/SaleDeliveryOpenApi/Create",
                            json,
                            "3uWZf0mu",
                            "F07A56582E5DDBC8F68358940138DBF5",
                            token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{ \"result\":\"参数合格！但是官方API访问失败，请咨询开发\" }";
            return result;
        }
        return result; // 创建成功  result:null
    }


    @Override
    public String createExpenseVoucher(Map<String,String> params){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 费用单 需要的所有内容
            String json = MapToJson.createExpenseDTO(params);
            result = HttpClient.HttpPost("/tplus/api/v2/expenseVoucher/CreateExpenseVoucher",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    params.get("token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建成功  result:null
        return result;
    }


    @Override
    public String createIncomeVoucher(Map<String,String> params){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 费用单 需要的所有内容
            String json = MapToJson.createIncomeDTO(params);
            result = HttpClient.HttpPost("/tplus/api/v2/incomeVoucher/CreateIncomeVoucher",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    params.get("token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建成功  result:null
        return result;
    }

    @Override
    public String createWLDW(Map<String,String> params){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            String json = MapToJson.createWLDWDTO(params);
            result = HttpClient.HttpPost("/tplus/api/v2/partner/Create",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    params.get("token"));
            if(result != null && !"".equals(result) && !result.contains("error")){
                result = "{ \"result\":\"创建成功！\" }";
            }else{
                result = "{ \"result\":\"程序异常，请检查参数和逻辑！ 对应的 往来单位 编号是：\" }" + params.get("Code");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！ 对应的 往来单位 编号是：\" }" + params.get("Code");
        }
    }


    @Override
    public String createDepartment(Map<String,String> params){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            String json = MapToJson.createDepartment(params);
            result = HttpClient.HttpPost("/tplus/api/v2/department/Create",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    params.get("token"));
            // result:"83"
            if(result != null && !"".equals(result) && result.contains("result")){
                result = "{ \"result\":\"创建成功！\" }";
            }else{
                result = "{ \"result\":\"程序异常，请检查参数和逻辑！ 对应的部门编号是：\" }" + params.get("Code");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！ 对应的 部门编号是：\" }" + params.get("Code");
        }
    }


    @Override
    public String createUser(Map<String,String> params){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            String json = MapToJson.createUser(params);
            result = HttpClient.HttpPost("tplus/api/v2/person/Create",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    params.get("token"));
            // result:"83"
            if(result != null && !"".equals(result) && result.contains("result")){
                result = "{ \"result\":\"创建成功！\" }";
            }else{
                result = "{ \"result\":\"程序异常，请检查参数和逻辑！ 对应的员工编号是：\" }" + params.get("code");
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！ 对应的 员工编号是：\" }" + params.get("code");
        }
    }


    @Override
    public String createInventory(SACsubJsonRootBean jrb, String token){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            String json = "";//MapToJson.createInventory(jrb);
            result = HttpClient.HttpPost("/tplus/api/v2/inventory/Create",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    token);
            if(result != null && "".equals(result)){
                result = "{ \"result\":\"创建成功！\" }";
            }else{
                //result = "{ \"result\":\"程序异常，请检查参数和逻辑！ 对应的商品编号是：\" }" + jrb.get("code");
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            //return "{ \"result\":\"程序异常，请重试！ 对应的商品编号是：\" }" + jrb.get("code");
        }
        return null;
    }

    public String getWarehouseList(String Code,String token){
        String result = "";
        try {
            String json = "";
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            if(Code != null && !"".equals(Code)){
                json = "{ param:{\"Code:\""+Code+"\"} }";
            }else{
                json = "{\n" +
                        "  \"param\": {\n" +
                        "\n" +
                        "  }\n" +
                        "}";
            }
            result = HttpClient.HttpPost("/tplus/api/v2/warehouse/Query",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    token);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！\" }";
        }
    }

    public String getSaPuOrderList(String Code,String startDate,String endDate,String token){
        String result = "";
        try {
            String json = "";
            //关键是传入的这个 map 必须要包含 往来单位 需要的所有内容
            if(Code != null && !"".equals(Code)){
                json = "{ param:{\"voucherCode:\""+Code+"\"} }";
            }else{
                json = "{ param:{ } }";
            }
            /*result = HttpClient.HttpPost("/tplus/api/v2/SaleOrderOpenApi/GetVoucherDTO",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    token);*/
            List<Map<String,Object>> map = orderMapper.getSaPuOrderList(Code,startDate,endDate);
            result = JSONObject.toJSONString(map);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！\" }";
        }
    }


    public String getBankNameList(String token){
        String result = "";
        try {
            List<Map<String,Object>> map = orderMapper.getBankNameList();
            result = JSONObject.toJSONString(map);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请重试！ 对应的商品编号是：\" }";
        }
    }

    public String createPurchaseArrival(XcxPuParam xcxPuParam, String token){
        String result = "";
        try {
            //关键是传入的这个 map 必须要包含 销售订单 需要的所有内容
            Map<String,Object> userMap = orderMapper.getUserInfoByMobile(xcxPuParam.getDto().getClerkMobile());
            String json = MapToJson.createPUOrderDTO(xcxPuParam,userMap);
            result = HttpClient.HttpPost(
                    "/tplus/api/v2/saleOrder/Create",
                    json,
                    "3uWZf0mu",
                    "F07A56582E5DDBC8F68358940138DBF5",
                    token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // 创建成功  result:null
    }
}