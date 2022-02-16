package com.yang.wsClient.service;

public interface IWSClient {
    String callWebSV(String wsdUrl, String namespaceURI,String method,String userName,String password, String... params) throws Exception;
}
