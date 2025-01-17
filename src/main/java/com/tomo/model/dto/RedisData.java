package com.tomo.model.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomo.service.JsonService;
import lombok.Data;

@Data
public class RedisData {
    String data;
    long saveTime;

    public RedisData(Object data) {
        this.data = JsonService.toJson(data);
        saveTime = System.currentTimeMillis();
    }

    public <T> T getData(TypeReference<T> typeReference) {
        return JsonService.fromJson(data, typeReference);
    }

    public static RedisData build(Object data){
        return new RedisData(data);
    }


}
