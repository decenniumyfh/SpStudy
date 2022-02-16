package com.yang.wsService.service.impl;

import com.yang.wsService.annotation.AutoPublish;
import com.yang.wsService.service.SimpleTest;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(endpointInterface = "com.yang.wsService.service.SimpleTest", serviceName = "SimpleTest")
@AutoPublish(publishAddr = "test")
@Component
public class SimpleTestImpl implements SimpleTest {
    @Override
    public String getTestStr(String name, String age) {
        String str = String.format("名字是：%s，年纪是：%s。", name, age);
        return str;
    }
}
