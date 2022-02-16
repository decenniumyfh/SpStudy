package com.yang.wsClient.controller;

import com.yang.wsClient.service.IWSClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private IWSClient wSClient;
    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "/test")
    public String test(){
        String[] paramArray = new String[2];
        paramArray[0]="1";
        paramArray[1]="2";
        return wSClient.callWebSV("http://127.0.0.1:20801/wsService/services/test?wsdl","http://service.wsService.yang.com/","getTestStr","","",paramArray);

    }

}
