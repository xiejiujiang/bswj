package com.example.bswj.controller;

import com.example.bswj.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.example.bswj.controller.TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping(value="/user", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView Info(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        LOGGER.info("--------------------------- 开始！是谁打开了江哥的页面！---------------------------");
        mav.setViewName("user");
        return mav; //返回user.html
    }


    @RequestMapping(value="/test", method = {RequestMethod.GET,RequestMethod.POST})
    public void test(HttpServletRequest request, HttpServletResponse response) {
        String str = request.getParameter("str");
        testService.testService(str);
    }
}
