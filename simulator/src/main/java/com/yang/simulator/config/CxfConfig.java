package com.yang.simulator.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.ext.logging.event.LogEvent;
import org.apache.cxf.ext.logging.event.LogEventSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;


@Configuration
@Slf4j
public class CxfConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setSender(new CustomEventSender());
        SpringBus bus = new SpringBus();
        bus.getFeatures().add(loggingFeature);
        return bus;
    }



    class CustomEventSender implements LogEventSender {
        @Override
        public void send(LogEvent event) {
            log.info("=================================开始获取报文信息=============================");
            log.info(String.format("Type：%s", event.getType().toString()));
            log.info(String.format("Address：%s", event.getAddress()));
            log.info(String.format("HttpMethod：%s", event.getHttpMethod()));
            log.info(String.format("Content-Type：%s", event.getContentType()));
            log.info(String.format("ResponseCode：%s", event.getResponseCode()));
            log.info(String.format("ExchangeId：%s", event.getExchangeId()));
            log.info(String.format("MessageId：%s", event.getMessageId()));
            if (event.getServiceName() != null) {
                log.info(String.format("ServiceName：%s", localPart(event.getServiceName())));
                log.info(String.format("PortName：%s", localPart(event.getPortName())));
                log.info(String.format("PortTypeName：%s", localPart(event.getPortTypeName())));
            }
            if (event.getFullContentFile() != null) {
                log.info("FullContentFile：%s", event.getFullContentFile().getAbsolutePath());
            }
            log.info("Headers:", event.getHeaders().toString());
            log.info(String.format("报文：%s", event.getPayload()));
            log.info("=================================获取报文信息完成了！=================================");
        }

        private String localPart(QName name) {
            return name == null ? null : name.getLocalPart();
        }
    }
}
