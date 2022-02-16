package com.yang.wsClient.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

public class LoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    /**
     * 用户名密码
     */
    private String username;
    private String password;
    public LoginInterceptor(String username, String password) {
        //设置在发送请求前阶段进行拦截
        super(Phase.PREPARE_SEND);
        this.username=username;
        this.password=password;
    }

    /**
     *
     */
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List<Header> headers = soapMessage.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElementNS("http://cxf.wolfcode.cn/","SecurityHeader");
        Element userName = doc.createElement("username");
        Element userPass = doc.createElement("password");

        userName.setTextContent(username);
        userPass.setTextContent(password);

        auth.appendChild(userName);
        auth.appendChild(userPass);

        headers.add(0, new Header(new QName("SecurityHeader"),auth));
    }
}
