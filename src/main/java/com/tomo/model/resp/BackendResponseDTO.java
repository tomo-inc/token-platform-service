package com.tomo.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BackendResponseDTO<T> {

    private T result;
    private Integer code;
    private String message;

}
