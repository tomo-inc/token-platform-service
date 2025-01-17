package com.tomo.model.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;


@Data
@Accessors(chain = true)
public class BitqueryReq {
    private String query;

    private Map<String, Object> variables;

    private String mode;
}
