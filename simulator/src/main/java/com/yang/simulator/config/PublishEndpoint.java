package com.yang.simulator.config;

import com.yang.simulator.annotation.AutoPublish;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Slf4j
public class PublishEndpoint implements ApplicationRunner {
    @Autowired
    private WebApplicationContext webApplicationConnect;

    @Autowired()
    @Qualifier(Bus.DEFAULT_BUS_ID)
    private SpringBus bus;

    @SuppressWarnings("resource")
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始进行自动发布webservice接口");
        String[] beanNames = webApplicationConnect.getBeanNamesForAnnotation(AutoPublish.class);
        for(String beanName : beanNames) {
            String publishAddr = webApplicationConnect.getType(beanName).getAnnotation(AutoPublish.class).publishAddr();
            EndpointImpl endpointImpl = new EndpointImpl(bus, webApplicationConnect.getBean(beanName));

            endpointImpl.publish(publishAddr);
            log.info(String.format("发布的接口地址：[%s]", publishAddr));
        }
        log.info("完成webservice接口自动发布");
    }
}
