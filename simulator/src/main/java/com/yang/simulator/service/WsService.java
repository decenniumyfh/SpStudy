package com.yang.simulator.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface WsService {
    public String getUserInfo(@WebParam(name = "name") String name, @WebParam(name = "age") String age);
}
