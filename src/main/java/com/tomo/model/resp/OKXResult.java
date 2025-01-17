package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OKXResult<T> {
    private Long code;
    private T data;
    private String detailMsg;
    private String errorCode;
    private String errorMessage;
    private String msg;
}