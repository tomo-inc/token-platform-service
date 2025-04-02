package com.tomo.service.market.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomo.annotation.Json;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
@Data
public class LoadJsonProcessor implements BeanPostProcessor {

    private final ObjectMapper objectMapper;

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Json annotation = field.getAnnotation(Json.class);
            if (annotation != null) {
                String path = annotation.value();
                ClassPathResource classPathResource = new ClassPathResource(path);
                try {
                    Object value = readJsonValue(classPathResource, field);
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, value);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load JSON from " + path, e);
                }
            }
        }
        return bean;
    }

    private Object readJsonValue(ClassPathResource resource, Field field) throws IOException {
        Class<?> fieldType = field.getType();
        if (List.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType parameterizedType) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class<?> listType) {
                    return objectMapper.readValue(
                      resource.getInputStream(),
                      objectMapper.getTypeFactory().constructCollectionType(List.class, listType)
                    );
                }
            }
        }
        return objectMapper.readValue(resource.getInputStream(), fieldType);
    }
}
