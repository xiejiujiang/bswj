package com.example.bswj.service.impl;

import com.example.bswj.mapper.orderMapper;
import com.example.bswj.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.example.bswj.service.impl.TestServiceImpl.class);

    @Autowired
    private orderMapper orderMapper;


    @Override
    public void testService(String str) {
        LOGGER.error("--------------------------- str == " + str + " ---------------------------");
    }
}
