package com.example.bswj.controller;

import com.example.bswj.mapper.orderMapper;
import com.example.bswj.service.TokenService;
import com.example.bswj.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
@Controller
public class SaticScheduleTask {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private orderMapper orderMapper;

    //每天凌晨6点执行
    @Scheduled(cron = "0 59 23 * * ?")
    private void configureTasks() {
        System.err.println("-------------------- 执行静态定时任务开始: " + LocalDateTime.now() + "--------------------");
        try{
            tokenService.refreshToken();
            try {
                String token = orderMapper.getTokenByAppKey("LwInPd77");//3uWZf0mu(测试001)
                List<Map<String,Object>> undeallist = orderMapper.getfordealrepeat();
                for(Map<String,Object> map : undeallist){
                    String code = map.get("code").toString();
                    String jsonn = map.get("jsonn").toString();
                    String apiresult = HttpClient.HttpPost(
                            "/tplus/api/v2/saleOrder/Create",
                            jsonn,
                            "LwInPd77",
                            "0C789D68F375CE6C1DC026DD1BAD2115",
                            token);
                    System.err.println("code == "+code+"的定时任务调用T+ 创建销售订单API的返回： apiresult == " + apiresult);
                    if(apiresult == null || "null".equals(apiresult)){
                        orderMapper.updatefordealrepeat(code);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("-------------------- 执行静态定时任务结束: " + LocalDateTime.now() + "--------------------");
    }
}