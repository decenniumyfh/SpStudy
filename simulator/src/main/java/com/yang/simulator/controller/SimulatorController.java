package com.yang.simulator.controller;

import com.yang.simulator.common.ResultUtil;
import com.yang.simulator.service.SimulatorService;
import com.yang.simulator.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/simulator")
@Slf4j
public class SimulatorController {
    @Autowired
    private SimulatorService simulatorService;

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity upload(MultipartFile file){

        return ResponseEntity.ok(ResultUtil.getSuccess(simulatorService.upload(file)));
    }

    @PostMapping("login")
    @ResponseBody
    public ResponseEntity login(@RequestBody User user){
        return ResponseEntity.ok(ResultUtil.getSuccess(simulatorService.login(user)));
    }

    @PostMapping("login2")
    @ResponseBody
    public ResponseEntity login2(@RequestBody User user){

        return ResponseEntity.ok(ResultUtil.getSuccess(simulatorService.login2(user)));
    }

    @PostMapping("test")
    @ResponseBody
    public ResponseEntity test(){

        return ResponseEntity.ok(ResultUtil.getSuccess(simulatorService.test()));
    }





}
