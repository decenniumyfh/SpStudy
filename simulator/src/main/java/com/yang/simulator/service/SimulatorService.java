package com.yang.simulator.service;

import com.yang.simulator.common.Result;
import com.yang.simulator.vo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface SimulatorService {
    Map<String,Object> upload(MultipartFile file);

    Map<String,Object> login(User user);

    Map<String,Object> login2(User user);

    Object test();

    Map<String,Object> restfulTest(String id);
}
