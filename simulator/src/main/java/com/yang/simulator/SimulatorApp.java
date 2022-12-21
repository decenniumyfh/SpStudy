package com.yang.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SimulatorApp {

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApp.class,args);
    }
}
