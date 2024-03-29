package com.example.bswj.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.bswj.Inventorysubscribe.InventoryJsonRootBean;
import com.example.bswj.SAsubscribe.SACsubJsonRootBean;
import com.example.bswj.entity.xcx.XcxSaParam;
import com.example.bswj.entity.xcxpu.XcxPuParam;
import com.example.bswj.mapper.orderMapper;
import com.example.bswj.service.BasicService;
import com.example.bswj.service.TestService;
import com.example.bswj.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "/token")
public class TokenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private BasicService basicService;

    @Autowired
    private orderMapper orderMapper;


    //这个里面 主要 用来 接受 code ,刷新 token ，更新对应的数据库
    @RequestMapping(value="/recode", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String recode(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 正式OAuth回调地址 -------------------");
        String code = request.getParameter("code");
        //第一次授权后，会有这个code,立刻调用 一次 授权码换token接口 ，拿到完整的 token 相关信息，并写入数据库。
        //3月17日思考： 暂时不用接口来访问，直接在线访问后 拿到第一次的数据，并 复制 填入数据库表中接口（后续定时任务来更新）
        return code;
    }



    //给宏勇二开做的，自动生成 03下面 没有生成的BOM 生成BOM
    @RequestMapping(value="/bom", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String bom(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 自动生成BOM -------------------");
        List<String> noBomCodelist = orderMapper.getHYnoBomCodelist();
        for(String inventoryCode : noBomCodelist){
            try{
                //通过存货code获取对应的材料明细code
                Map<String,Object> paramsMap = orderMapper.getBomMapByCode(inventoryCode);
                if(paramsMap != null ){// 说明 这个成品的编码 以及 对应 自定项的编码都拿到了
                    //通过JAVA再来更新 这个存货的名称 和 助记码
                    String priuserdefnvc1 = paramsMap.get("priuserdefnvc1").toString();
                    String priuserdefnvc2 = paramsMap.get("priuserdefnvc2").toString();
                    String priuserdefnvc3 = paramsMap.get("priuserdefnvc3").toString();
                    String priuserdefnvc4 = paramsMap.get("priuserdefnvc4").toString();
                    if(priuserdefnvc3.equals(priuserdefnvc4)){
                        //name = @jinjiaoa + '-' + @gangban + '-' + @jicai
                        String newName = priuserdefnvc3 + "-"+priuserdefnvc2+"-"+priuserdefnvc1;
                        paramsMap.put("newName",newName);
                        String zjm = ChineseCharacterUtil.getUpperCase(newName,false).toUpperCase();
                        paramsMap.put("zjm",zjm);
                        //orderMapper.updateInventoryName(paramsMap);
                    }else{
                        //name = @jinjiaoa + '/' + @jinjiaob + '-' + @gangban + '-' + @jicai
                        String newName = priuserdefnvc3 + "/" + priuserdefnvc4 + "-"+priuserdefnvc2+"-"+priuserdefnvc1;
                        paramsMap.put("newName",newName);
                        String zjm = ChineseCharacterUtil.getUpperCase(newName,false).toUpperCase();
                        paramsMap.put("zjm",zjm);
                        //orderMapper.updateInventoryName(paramsMap);
                    }
                }

                paramsMap.put("yurucode",orderMapper.getDefaultWarehouseByCode(inventoryCode));
                paramsMap.put("yuchu1",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena1code").toString()));
                paramsMap.put("yuchu3",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena3code").toString()));
                paramsMap.put("yuchu4",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena4code").toString()));


                String token = orderMapper.getTokenByAppKey("vuaNrz8F");//宏勇二开的 appkey
                //销售订单的JSON
                String json = MapToJson.createBomJson(paramsMap);
                LOGGER.info("调用T+ 创建BOM 的json == " + json);
                String apiresult1 = HttpClient.HttpPost(
                        "/tplus/api/v2/bom/Create",
                        json,
                        "vuaNrz8F",//宏勇二开的 appkey
                        "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                        token);
                LOGGER.info("1调用T+ 创建BOM的返回： apiresult1 == " + apiresult1);//1调用T+ 创建BOM的返回： apiresult1 == "8"
                if(apiresult1 != null && !"".equals(apiresult1) && Integer.valueOf(apiresult1.replaceAll("\"","")) > 0){//成功！
                    LOGGER.info("1 存货code ====== " + inventoryCode + " 的BOM创建成功！");
                    //创建成功的话，就把 BOM子件的领料工序 固定成 1 ： idprocess = 1
                    orderMapper.updateChildBomProcess(apiresult1.replaceAll("\"",""));
                    //调用下 审核接口啊
                    HttpClient.HttpPost(
                            "/tplus/api/v2/bom/Audit",
                            "{\n" +
                                    "  \"dto\": {\n" +
                                    "    \"Version\": \"1.0\",\n" +
                                    "    \"Code\": \""+inventoryCode+"\"\n" +
                                    "  }\n" +
                                    "}",
                            "vuaNrz8F",//宏勇二开的 appkey
                            "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                            token);
                }else{
                    //说明 访问接口失败了，就 再来一次
                    String apiresult2 = HttpClient.HttpPost(
                            "/tplus/api/v2/bom/Create",
                            json,
                            "vuaNrz8F",//宏勇二开的 appkey
                            "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                            token);
                    LOGGER.info("2调用T+ 创建销售订单API的返回： apiresult2 == " + apiresult2);
                    if(apiresult2 != null && !"".equals(apiresult2) && Integer.valueOf(apiresult2.replaceAll("\"","")) > 0){//成功！
                        LOGGER.info("2 存货code ====== " + inventoryCode + " 的BOM创建成功！");
                        orderMapper.updateChildBomProcess(apiresult2.replaceAll("\"",""));
                        //调用下 审核接口啊
                        HttpClient.HttpPost(
                                "/tplus/api/v2/bom/Audit",
                                "{\n" +
                                        "  \"dto\": {\n" +
                                        "    \"Version\": \"1.0\",\n" +
                                        "    \"Code\": \""+inventoryCode+"\"\n" +
                                        "  }\n" +
                                        "}",
                                "vuaNrz8F",//宏勇二开的 appkey
                                "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                                token);
                    }else{
                        String failueresult = JSONObject.parseObject(apiresult2).getString("message");
                        LOGGER.info(" 存货code ====== " + inventoryCode + " 的BOM创建失败！原因：" + failueresult);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "自动生成BOM";
    }

    //T+ 的 消息订阅的接口。
    @RequestMapping(value="/ticket", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String reticket(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 正式消息接收地址，包含 ticket，消息订阅，授权 -------------------");
        try{
            InputStreamReader reader=new InputStreamReader(request.getInputStream(),"utf-8");
            BufferedReader buffer=new BufferedReader(reader);
            String params=buffer.readLine();
            JSONObject jsonObject = JSONObject.parseObject(params);
            String encryptMsg = jsonObject.getString("encryptMsg");
            String destr = AESUtils.aesDecrypt(encryptMsg,"123456789012345x");
            //String destr = AESUtil.decrypt(encryptMsg,"123456789012345x");
            // {"id":"AC1C04B100013301500B4A9B012DB2EC","appKey":"A9A9WH1i","appId":"58","msgType":"SaleDelivery_Audit","time":"1649994072443","bizContent":{"externalCode":"","voucherID":"23","voucherDate":"2022/4/15 0:00:00","voucherCode":"SA-2022-04-0011"},"orgId":"90015999132","requestId":"86231b63-f0c2-4de1-86e9-70557ba9cd62"}
            JSONObject job = JSONObject.parseObject(destr);
            //这个接口 专门用于 宏勇二开的。 自动生成BOM 的
            if(job !=null && job.getString("msgType") != null && "Inventory_Create".equals(job.getString("msgType"))){
                InventoryJsonRootBean ijb =  job.toJavaObject(InventoryJsonRootBean.class);//存货的订阅信息DTO
                String inventoryCode = ijb.getBizContent().getCode();
                //如果是产成品下面的 存货创建，才会创建BOM
                if("03".equals(orderMapper.getInventoryLevel1ClassCode(inventoryCode))){
                    //通过存货code获取对应的材料明细code
                    Map<String,Object> paramsMap = orderMapper.getBomMapByCode(inventoryCode);
                    if(paramsMap != null ){// 说明 这个成品的编码 以及 对应 自定项的编码都拿到了
                        //通过JAVA再来更新 这个存货的名称 和 助记码
                        String priuserdefnvc1 = paramsMap.get("priuserdefnvc1").toString();
                        String priuserdefnvc2 = paramsMap.get("priuserdefnvc2").toString();
                        String priuserdefnvc3 = paramsMap.get("priuserdefnvc3").toString();
                        String priuserdefnvc4 = paramsMap.get("priuserdefnvc4").toString();
                        if(priuserdefnvc3.equals(priuserdefnvc4)){
                            //name = @jinjiaoa + '-' + @gangban + '-' + @jicai
                            String newName = priuserdefnvc3 + "-"+priuserdefnvc2+"-"+priuserdefnvc1;
                            paramsMap.put("newName",newName);
                            String zjm = ChineseCharacterUtil.getUpperCase(newName,false).toUpperCase();
                            paramsMap.put("zjm",zjm);
                            orderMapper.updateInventoryName(paramsMap);
                        }else{
                            //name = @jinjiaoa + '/' + @jinjiaob + '-' + @gangban + '-' + @jicai
                            if(priuserdefnvc4 != null && !"".equals(priuserdefnvc4)){
                                String newName = priuserdefnvc3 + "/" + priuserdefnvc4 + "-"+priuserdefnvc2+"-"+priuserdefnvc1;
                                paramsMap.put("newName",newName);
                                String zjm = ChineseCharacterUtil.getUpperCase(newName,false).toUpperCase();
                                paramsMap.put("zjm",zjm);
                                orderMapper.updateInventoryName(paramsMap);
                            }else{
                                String newName = priuserdefnvc3 + "-"+priuserdefnvc2+"-"+priuserdefnvc1;
                                paramsMap.put("newName",newName);
                                String zjm = ChineseCharacterUtil.getUpperCase(newName,false).toUpperCase();
                                paramsMap.put("zjm",zjm);
                                orderMapper.updateInventoryName(paramsMap);
                            }
                        }
                    }

                    paramsMap.put("yurucode",orderMapper.getDefaultWarehouseByCode(inventoryCode));
                    paramsMap.put("yuchu1",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena1code").toString()));
                    paramsMap.put("yuchu3",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena3code").toString()));
                    paramsMap.put("yuchu4",orderMapper.getDefaultWarehouseByCode(paramsMap.get("invena4code").toString()));


                    String token = orderMapper.getTokenByAppKey("vuaNrz8F");//宏勇二开的 appkey
                    //销售订单的JSON
                    String json = MapToJson.createBomJson(paramsMap);
                    LOGGER.info("调用T+ 创建BOM 的json == " + json);
                    String apiresult1 = HttpClient.HttpPost(
                            "/tplus/api/v2/bom/Create",
                            json,
                            "vuaNrz8F",//宏勇二开的 appkey
                            "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                            token);
                    LOGGER.info("1调用T+ 创建BOM的返回： apiresult1 == " + apiresult1);//1调用T+ 创建BOM的返回： apiresult1 == "8"
                    if(apiresult1 != null && !"".equals(apiresult1) && Integer.valueOf(apiresult1.replaceAll("\"","")) > 0){//成功！
                        LOGGER.info("1 存货code ====== " + inventoryCode + " 的BOM创建成功！");
                        //创建成功的话，就把 BOM子件的领料工序 固定成 1 ： idprocess = 1
                        orderMapper.updateChildBomProcess(apiresult1.replaceAll("\"",""));
                        //调用下 审核接口啊
                        HttpClient.HttpPost(
                                "/tplus/api/v2/bom/Audit",
                                 "{\n" +
                                         "  \"dto\": {\n" +
                                         "    \"Version\": \"1.0\",\n" +
                                         "    \"Code\": \""+inventoryCode+"\"\n" +
                                         "  }\n" +
                                         "}",
                                "vuaNrz8F",//宏勇二开的 appkey
                                "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                                token);
                    }else{
                        //说明 访问接口失败了，就 再来一次
                        String apiresult2 = HttpClient.HttpPost(
                                "/tplus/api/v2/bom/Create",
                                json,
                                "vuaNrz8F",//宏勇二开的 appkey
                                "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                                token);
                        LOGGER.info("2调用T+ 创建销售订单API的返回： apiresult2 == " + apiresult2);
                        if(apiresult2 != null && !"".equals(apiresult2) && Integer.valueOf(apiresult2.replaceAll("\"","")) > 0){//成功！
                            LOGGER.info("2 存货code ====== " + inventoryCode + " 的BOM创建成功！");
                            orderMapper.updateChildBomProcess(apiresult2.replaceAll("\"",""));
                            //调用下 审核接口啊
                            HttpClient.HttpPost(
                                    "/tplus/api/v2/bom/Audit",
                                    "{\n" +
                                            "  \"dto\": {\n" +
                                            "    \"Version\": \"1.0\",\n" +
                                            "    \"Code\": \""+inventoryCode+"\"\n" +
                                            "  }\n" +
                                            "}",
                                    "vuaNrz8F",//宏勇二开的 appkey
                                    "7255D06CA9195488A95988A1D790D84B",//宏勇二开的 AppSecret
                                    token);
                        }else{
                            String failueresult = JSONObject.parseObject(apiresult2).getString("message");
                            LOGGER.info(" 存货code ====== " + inventoryCode + " 的BOM创建失败！原因：" + failueresult);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{ \"result\":\"success\" }";
    }

    // ------------------------------------------------  以下是业务接口 -----------------------------------------------------//

    //T+ 的 存货 创建接口
    @RequestMapping(value="/createInventory", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createInventory(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的存货创建API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today);
            String sign = request.getParameter("sign");
            if(sign != null && !"".equals(sign)){
                String desStr = EncryptUtil.decrypt(sign,key);
                JSONObject job = JSONObject.parseObject(desStr);
                SACsubJsonRootBean jrb =  job.toJavaObject(SACsubJsonRootBean.class);//存货 接受 的参数 DTO
                String token = orderMapper.getTokenByAppKey("3uWZf0mu");
                return basicService.createInventory(jrb,token);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\" T+存货创建 程序异常，请重试！\" } ";
        }
    }


    //T+ 的 员工 创建接口
    @RequestMapping(value="/createUser", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createUser(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的员工创建API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today);
            String sign = request.getParameter("sign");
            if(sign != null && !"".equals(sign)){
                String desStr = EncryptUtil.decrypt(sign,key);
                JSONObject job = JSONObject.parseObject(desStr);
                SACsubJsonRootBean jrb =  job.toJavaObject(SACsubJsonRootBean.class);//员工 接受 的参数 DTO
                String token = orderMapper.getTokenByAppKey("3uWZf0mu");

                /*Map<String,String> params = new HashMap<String,String>();
                params.put("token",token);//请求token
                params.put("departmentCode",departmentCode);
                params.put("Code",code);
                params.put("Name",name);
                params.put("MobilePhoneNo",MobilePhoneNo);
                basicService.createUser(jrb,token);
                */
                return null;
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"T+ 员工 程序异常，请重试！\" }";
        }
    }


    //T+ 的 部门 创建接口
    @RequestMapping(value="/createDepartment", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createDepartment(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的部门创建API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String sign = request.getParameter("sign");
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String parentCode = request.getParameter("parentCode");
            String token = orderMapper.getTokenByAppKey("3uWZf0mu");

            if(sign != null && !"".equals(sign) && name != null && !"".equals(name) && code != null && !"".equals(code)
                    && parentCode != null && !"".equals(parentCode)
                    && sign.equals(Md5.md5(code+name+parentCode+today))){
                Map<String,String> params = new HashMap<String,String>();
                params.put("token",token);//请求token
                params.put("parentCode",parentCode);
                params.put("Code",code);
                params.put("Name",name);
                return basicService.createDepartment(params);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"T+ 部门 程序异常，请重试！\" }";
        }
    }


    //T+ 的 往来单位 创建接口
    @RequestMapping(value="/createPatner", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createPatner(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的 往来单位 创建API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String sign = request.getParameter("sign");
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            String parentCode = request.getParameter("parentCode");
            String token = orderMapper.getTokenByAppKey("LwInPd77");

            if(sign != null && !"".equals(sign) && name != null && !"".equals(name) && code != null && !"".equals(code)
                    && parentCode != null && !"".equals(parentCode)
                    && sign.equals(Md5.md5(code+name+parentCode+today))){
                Map<String,String> params = new HashMap<String,String>();
                params.put("token",token);//请求token

                return basicService.createWLDW(params);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"T+ 往来单位 程序异常，请重试！\" }";
        }
    }

    //------------------------------------------------------------------------------------------------------------//

    //T+ 的 销售订单 创建接口
    @RequestMapping(value="/createSaPuOrder", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createSaPuOrder(@RequestBody String sign,HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的 销售订单 创建API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today).substring(0,16);
            //String sign = request.getParameter("sign");
            sign = URLDecoder.decode(sign,"UTF-8");
            //LOGGER.info("-------- 销售订单api (转义处理前) sign == " + sign);
            sign = sign.replaceAll(" ","+");
            sign = sign.replaceAll("sign=","");
            //LOGGER.info("-------- 销售订单api (转义处理后) sign == " + sign);
            String token = orderMapper.getTokenByAppKey("LwInPd77");//3uWZf0mu(测式001)
            if(sign != null && !"".equals(sign)){
                String parmaJosnStr = EncryptUtil.decrypt(sign,key);
                LOGGER.info("-------- 小程序订单信息解密后的Str == " + parmaJosnStr);
                JSONObject job = JSONObject.parseObject(parmaJosnStr);
                XcxSaParam xcxSaParam =  job.toJavaObject(XcxSaParam.class);//销货单的订阅信息DTO
                List<Map<String,Object>> saPuOrderList = orderMapper.getSaPuOrderList(xcxSaParam.getCode(),"","");
                if("15".equals(xcxSaParam.getBusinessType()) && saPuOrderList != null && saPuOrderList.size() != 0){
                    return "{ \"result\":\"订单已存在！\" }";
                }else{
                    return basicService.createSaPuOrder(xcxSaParam,token);
                }
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请咨询开发！\"}";
        }
    }



    //仓库查询接口
    @RequestMapping(value="/getWarehouseList", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String getWarehouseList(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了 获取仓库列表 API  -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today).substring(0,16);
            String sign = request.getParameter("sign");

            //LOGGER.info("-------- 获取仓库列表(空格转义前) sign == " + sign);
            sign = sign.replaceAll(" ","+");
            //LOGGER.info("-------- 获取仓库列表(空格转义后) sign == " + sign);

            String token = orderMapper.getTokenByAppKey("LwInPd77");
            if(sign != null && !"".equals(sign)){
                String parmaJosnStr = EncryptUtil.decrypt(sign,key);
                LOGGER.info("-------- 获取仓库列表解密后的Str == " + parmaJosnStr);
                JSONObject job = JSONObject.parseObject(parmaJosnStr);
                String Code = job.getString("Code");//仓库 code
                return basicService.getWarehouseList(Code,token);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请咨询开发！\"}";
        }
    }

    //订单查询接口
    @RequestMapping(value="/getSaPuOrderList", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String getSaPuOrderList(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了 获取销售订单列表 API  -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today).substring(0,16);
            String sign = request.getParameter("sign");
            sign = sign.replaceAll(" ","+");

            if(sign != null && !"".equals(sign)){
                String parmaJosnStr = EncryptUtil.decrypt(sign,key);
                LOGGER.info("-------- 获取销售订单列表解密后的Str == " + parmaJosnStr);
                if(parmaJosnStr == null || "".equals(parmaJosnStr) ){
                    return basicService.getSaPuOrderList("","","");
                }else{
                    JSONObject job = JSONObject.parseObject(parmaJosnStr);
                    String Code = job.getString("Code"); // 订单编号
                    String startDate = ""+job.getString("StartDate");//开始时间
                    String endDate = ""+job.getString("EndDate");//结束时间
                    return basicService.getSaPuOrderList(Code,startDate,endDate);
                }
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请咨询开发！\"}";
        }
    }

    //账号获取 列表 API
    @RequestMapping(value="/getBankNameList", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String getBankNameList(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了 账号获取 列表 API  -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today).substring(0,16);
            String sign = request.getParameter("sign");

            //LOGGER.info("-------- 账号获取列表(空格转义前) sign == " + sign);
            sign = sign.replaceAll(" ","+");
            //LOGGER.info("-------- 账号获取列表(空格转义后) sign == " + sign);

            String token = orderMapper.getTokenByAppKey("LwInPd77");
            if(sign != null && !"".equals(sign)){
                String parmaJosnStr = EncryptUtil.decrypt(sign,key);
                LOGGER.info("-------- 获取账号获取列表解密后的Str == " + parmaJosnStr);
                return basicService.getBankNameList(token);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请咨询开发！\"}";
        }
    }


    //T+ 的 进货单 创建接口
    @RequestMapping(value="/createPurchaseArrival", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String createPurchaseArrival(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("------------------- 小程序调用了T+的 进货单 创建接口 API -------------------");
        try{
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());//当日
            String key = Md5.md5(today).substring(0,16);
            String sign = request.getParameter("sign");

            LOGGER.info("-------- 获取进货单列表(空格转义前) sign == " + sign);
            sign = sign.replaceAll(" ","+");
            LOGGER.info("-------- 获取进货单列表(空格转义后) sign == " + sign);

            String token = orderMapper.getTokenByAppKey("LwInPd77");
            if(sign != null && !"".equals(sign)){
                String parmaJosnStr = EncryptUtil.decrypt(sign,key);
                LOGGER.info("-------- 小程序进货单创建接口解密后的Str == " + parmaJosnStr );
                JSONObject job = JSONObject.parseObject(parmaJosnStr);
                XcxPuParam xcxPuParam =  job.toJavaObject(XcxPuParam.class);//进货单的订阅信息DTO
                return basicService.createPurchaseArrival(xcxPuParam,token);
            }else{
                return "{ \"result\":\"参数不合格！\" }";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"result\":\"程序异常，请咨询开发！\"}";
        }
    }
}