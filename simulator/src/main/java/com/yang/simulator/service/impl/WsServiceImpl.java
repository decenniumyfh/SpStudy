package com.yang.simulator.service.impl;

import com.yang.simulator.annotation.AutoPublish;
import com.yang.simulator.service.WsService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(endpointInterface = "com.yang.simulator.service.WsService", serviceName = "WsServiceTest")
@AutoPublish(publishAddr = "userInfo")
@Component
public class WsServiceImpl implements WsService {
    @Override
    public String getUserInfo(String name, String age) {
        String str = String.format("名字是：%s，年纪是：%s。", name, age);
        return str;
    }
}
