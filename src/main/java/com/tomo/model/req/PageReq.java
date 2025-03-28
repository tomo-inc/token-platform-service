package com.tomo.model.req;

import lombok.Data;

@Data
public class PageReq {

    /**
     * 页码，从1开始
     */
    private Integer page = 1;

    /**
     * 每页大小
     */
    private Integer size = 50;
}
