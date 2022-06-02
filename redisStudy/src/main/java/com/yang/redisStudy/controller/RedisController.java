
package com.yang.redisStudy.controller;

import com.yang.redisStudy.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @GetMapping(value = "/cache/user/cacheUser")
    public Map<String, Object> cacheUser() {
        Map<String,Object> result = new HashMap<String, Object>();
        User u = new User();
        u.setId("1");
        u.setName("test");
        result.put("body", u);
        redisCacheTemplate.opsForValue().set("1", u);
        return result;

    }


/**
     * 获取缓存信息
     * @param id
     * @return
     */
    @GetMapping(value = "/cache/user/getCacheUser/{id}")
    @ResponseBody
    public Map<String, Object> getCacheUser(@PathVariable Long id) {
        Map<String,Object> result = new HashMap<String, Object>();

        User u =  (User) redisCacheTemplate.opsForValue().get("1");
        result.put("body", u);
        return result;

    }
}

