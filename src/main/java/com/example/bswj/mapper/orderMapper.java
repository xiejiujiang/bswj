package com.example.bswj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface orderMapper {

    //返回数据库中 XXX_code_token 所有记录的 企业信息
    public List<Map<String,String>> getDBAllOrgList();

    //调用refreshtoken 后更新数据库
    public void updateOrgToken(Map<String,String> updateMap);


    //根据appkey 获取 当前的 token
    public String getTokenByAppKey(@Param("AppKey") String AppKey);

    //根据 OrgId 获取 当前的 AppKey 和 AppSecret
    public Map<String,String> getAppKeySecretByAppKey(@Param("OrgId") String OrgId);

    //根据单据编号 或者 附件 id
    public List<Map<String,String>> getfjidByCode(@Param("code")String code);

    //根据员工的编号 或者 对应的 手机号
    public String getMobileByCode(@Param("code")String code);

    //给这一单加上特殊标志
    public void updateTSAorderFlag(@Param("code")String code);

    //获取这一单加上特殊标志
    public String getTSAorderFlag(@Param("code")String code);


    //更新销货单指定字段的上传状态标志
    public void updateUploadHQState(Map<String,String> updateMap);

    public void updateTOrderMemo(Map<String,String> updateMap);

    //增加了 专门处理中德的红旗单据 消息订阅 未收到的问题
    List<Map<String,String>> getUnuploadList();
}
