package com.tomo.model.req;

import lombok.Data;

import java.util.List;

@Data
public class JsonRpcReq {
    private String jsonrpc = "2.0";
    private int id = 1;
    private String method;
    private List<Object> params;

}
