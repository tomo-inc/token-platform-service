package com.tomo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 配置序列化时忽略值为null的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 配置反序列化时忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置序列化时日期格式化为时间戳（可按需调整，例如设置为特定日期格式等）
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param object 要序列化的Java对象
     * @return JSON字符串，如果序列化失败则返回null
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JsonUtil.toJson error", e);
            return null;
        }
    }

    /**
     * 将JSON字符串反序列化为Java对象
     *
     * @param json    JSON字符串
     * @param clazz   目标Java类的Class对象
     * @return 反序列化后的Java对象，如果反序列化失败则返回null
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(json+" parseObject failed",e);
            return null;
        }
    }

    /**
     * 将JSON字符串转换为指定的泛型对象
     *
     * @param json JSON字符串
     * @param typeReference 泛型类型引用，例如：new TypeReference<Response<Person>>() {}
     * @param <T> 泛型类型参数
     * @return 转换后的泛型对象，如果转换失败则抛出异常
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference){
        try {
            // 配置序列化时忽略值为null的属性
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 配置反序列化时忽略未知属性
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 配置序列化时日期格式化为时间戳（可按需调整，例如设置为特定日期格式等）
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
            return objectMapper.readValue(json, typeReference);
        }catch (Exception e){
            log.error(json+" parseObject failed",e);
            return null;
        }

    }

    /**
     * 配置自定义的ObjectMapper，方便在外部根据特殊需求进一步调整配置
     *
     * @return 配置好的ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
