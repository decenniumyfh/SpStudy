package com.yang.simulator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class JsonUtil {
    // 定义jackson对象
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = mapper.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转换成json异常：:" + e.getMessage(), e);
        }
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            T t = mapper.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            throw new RuntimeException("json结果集转化为对象异常：:" + e.getMessage() + " beanType = " + beanType.getName(), e);
        }
    }

    /**
     * 将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            throw new RuntimeException("将json数据转换成pojo对象list异常：:" + e.getMessage(), e);
        }

    }

    /**
     * 格式化json
     *
     * @param object
     * @return
     */
    public static String toJsonWithDefaultPrettyPrinter(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转json字符串错误", e);
        }
    }


    public static <T> Map<String, Object> objectToMap(T t) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(t), Map.class);
    }
    public static <T> Map<String, Object> strToMap(String t) throws JsonProcessingException {
        return mapper.readValue(t, Map.class);
    }


}
