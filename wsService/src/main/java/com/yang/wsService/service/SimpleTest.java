package com.yang.wsService.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SimpleTest {
    public String getTestStr(@WebParam(name = "name") String name, @WebParam(name = "age") String age);

}
