package com.example.bswj.service;

import com.example.bswj.SAsubscribe.SACsubJsonRootBean;
import com.example.bswj.saentity.JsonRootBean;

import java.util.List;
import java.util.Map;

public interface BasicService {

    //这个里面 封装了 所有  T+ 基础类的 接口API 调用方法

    //创建项目
    public String createXM(Map<String,String> params);

    //查询项目
    public String getXM(Map<String,String> params);

    //创建项目分类
    public String createXMClass(Map<String,String> params);

    //查询项目分类
    public String getXMClass(Map<String,String> params);

    //查询仓库
    public String getWarehouse(Map<String,String> params);

    //创建仓库
    public String createWarehouse(Map<String,String> params);

    //创建凭证
    public String createPZ(Map<String,String> params);

    //查询销货单详情接口
    public JsonRootBean getSaOrder(Map<String,String> params);

    //根据 单据编号 返回 附件 ID
    public List<Map<String,String>> getfjidByCode(String code);

    public String unAuditZDorder(String voucherCode);

    //创建销售订单详情接口
    public String createSaPuOrder(Map<String,String> params);

    //创建费用单详情接口
    public String createExpenseVoucher(Map<String,String> params);

    //创建 收入单 详情接口
    public String createIncomeVoucher(Map<String,String> params);

    //创建往来单位
    public String createWLDW(Map<String,String> params);

    //创建部门
    public String createDepartment(Map<String,String> params);

    //创建员工
    public String createUser(Map<String,String> params);

    //创建商品
    public String createInventory(SACsubJsonRootBean jrb,String token);
}
