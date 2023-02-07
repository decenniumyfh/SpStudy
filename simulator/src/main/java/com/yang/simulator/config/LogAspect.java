package com.yang.simulator.config;

import cn.hutool.json.JSONUtil;
import com.yang.simulator.config.filter.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Aspect
@Component
@Slf4j
public class LogAspect {
    //忽略的url集合
    private static final List<String> IGNORE_LOG_URLS = Collections.emptyList();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.GetMapping)||"
            + "@annotation(org.springframework.web.bind.annotation.PutMapping)||"
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||"
            + "@annotation(org.springframework.web.bind.annotation.PatchMapping)||"
            + "@annotation(org.springframework.web.bind.annotation.Mapping)||"
            + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void mappingAspect() {
    }


    @Before("mappingAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        if (IGNORE_LOG_URLS.contains(request.getRequestURI())) {
            return;
        }
        if(attributes.getRequest().getMethod().equals("POST")){
            BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(attributes.getRequest());
            getRequInfo(joinPoint,requestWrapper);
        }else{
            getRequInfo(joinPoint,attributes.getRequest());
        }



    }

    private void getRequInfo(JoinPoint joinPoint,HttpServletRequest request) {
        String contentType = request.getContentType();
        StringBuilder logStr = new StringBuilder("\n--------------------------请求报文----------------------------\n");
        logStr.append("请求类型 : " + request.getMethod() + "，\t请求URL : " + request.getRequestURL().toString() + "\n");
        logStr.append("Header: " + JSONUtil.toJsonStr(getHeaderMap(request)) + "\n");
        logStr.append("Parameter: " + JSONUtil.toJsonStr(getParameterMap(request)) + "\n");
        logStr.append("调用方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                + "\t(" + joinPoint.getTarget().getClass().getSimpleName() + ".java:1)" + "\n"
                + "请求参数 : " + Arrays.toString(joinPoint.getArgs()) + "\n");
        if (null != contentType && contentType.contains("application/json")) {
            logStr.append("请求参数json格式：" + JSONUtil.toJsonStr(joinPoint.getArgs()) + "\n");
        }

        logStr.append("--------------------------------------------------------------");
        String result = logStr.toString().replaceAll("\n", "\n" + MDC.get("X-TraceId") + "\t|");
        log.info(result);
    }

    @AfterReturning(returning = "ret", pointcut = "mappingAspect()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {



    }
    private static Map<String, Object> getParameterMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            parameterMap.put(key, value);
        }
        return parameterMap;
    }

    private byte[] readBytes(InputStream is) throws IOException {
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

    private static Map<String, Object> getHeaderMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, Object> headerMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        return headerMap;
    }
}
