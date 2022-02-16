package com.yang.wsClient.service.impl;

import com.yang.wsClient.interceptor.LoginInterceptor;
import com.yang.wsClient.service.IWSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;

@Slf4j
@Component
public class WSClient implements IWSClient {
    @Override
    public String callWebSV(String wsdUrl, String namespaceURI, String method, String userName, String password, String... params)  {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        if(!wsdUrl.contains("?")) {
            wsdUrl += "?wsdl";
        }

        try(Client client = dcf.createClient(wsdUrl)) {
            // 需要密码的情况需要加上用户名和密码
            if(!StringUtils.isEmpty(userName)&&!StringUtils.isEmpty(password)) {
                client.getOutInterceptors().add(new LoginInterceptor(userName, password));
            }

            //如果有命名空间需要加上这个，第一个参数为命名空间名称，第二个参数为WebService方法名称
            QName operationName = new QName(namespaceURI,method);
            Object[] objects = client.invoke(operationName, params);
            if(objects==null) {
                return null;
            }
            return objects[0].toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage(),e.getCause());
        }
    }
}
