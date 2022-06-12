package com.yang.es.controller;

import com.yang.es.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EsService esService;
    @GetMapping("/test")
    public void test(){
        esService.test();
    }

}
