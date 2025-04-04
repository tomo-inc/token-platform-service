package com.tomo.model.resp;

import lombok.Data;

import java.util.List;

@Data
public class JsonRpcResp<T> {
    private String jsonrpc = "2.0";
    private int id = 1;
    private T result;
}
