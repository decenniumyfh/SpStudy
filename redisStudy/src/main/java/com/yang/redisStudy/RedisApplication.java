package com.yang.redisStudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RedisApplication {

    //https://blog.51cto.com/u_13538361/3284128
    public static void main(String[] args){
        SpringApplication.run(RedisApplication.class,args);
    }
}
