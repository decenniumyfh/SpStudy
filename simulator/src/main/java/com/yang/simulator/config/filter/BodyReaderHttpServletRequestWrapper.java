package com.yang.simulator.config.filter;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**  
 * @title: BodyReaderHttpServletRequestWrapper.java</p>  
 * @description:
 * @author yangfh  
 * @date 2018年4月19日下午5:12:17  
 */

@Slf4j
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    @SneakyThrows
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request)
              {
        super(request);
        if(request.getInputStream()!=null){
            body = readBytes(request.getInputStream());
        }else{
            body=null;
        }

    }


    @Override
    public BufferedReader getReader() throws IOException {
        ServletInputStream sis = getInputStream();
        if(sis!=null){
            return new BufferedReader(new InputStreamReader(sis));
        }else{
            return null;
        }
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(body==null){
            return null;
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    private byte[] readBytes(InputStream is) throws IOException{
        try (BufferedInputStream bis = new BufferedInputStream(is);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bodys = baos.toByteArray();
            return bodys;
        } catch (IOException ex) {
            throw ex;
        }
    }

}


