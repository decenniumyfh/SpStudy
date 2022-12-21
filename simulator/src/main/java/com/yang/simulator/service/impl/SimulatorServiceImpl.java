package com.yang.simulator.service.impl;

import com.yang.simulator.service.SimulatorService;
import com.yang.simulator.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class SimulatorServiceImpl implements SimulatorService {
    private static final long MAX_FILE_SIZE = 10485760;

    @Override
    public Map<String,Object> upload(MultipartFile file) {
        Objects.requireNonNull(file,"上传文件不允许为空");
        Map<String,Object> resultMaps = new HashMap<String,Object>();
        resultMaps.put("size",file.getSize());
        resultMaps.put("filename",file.getOriginalFilename());
        return resultMaps;
    }

    @Override
    public Map<String, Object> login(User user) {
        Objects.requireNonNull(user,"登录信息不允许为空");

        Map<String,Object> resultMaps = new HashMap<String,Object>();
        resultMaps.put("user",user);
        return resultMaps;
    }

    @Override
    public Map<String, Object> login2(User user) {
        Objects.requireNonNull(user,"登录信息不允许为空");

        Map<String,Object> resultMaps = new HashMap<String,Object>();
        resultMaps.put("user",user);
        return resultMaps;
    }

    @Override
    public Object test() {
        String key = "d38c3da5a6837dae733392b1219f9733";
        String src = "test";
        //AESUtil util = new AESUtil(AESUtil.CipherMode.ECB, AESUtil.PaddingMode.PKCS5Padding,key,null);
        //System.out.println(util.encrypt(src));
        //System.out.println(util.decrypt("5DC1AEC678C6C9DD431EC4E6F6FFDC7F"));
        return true;
    }


}
