package com.tomo.model.req;

import lombok.Data;

@Data
public class TokenReq {
    Long chainId;
    String address;
}
