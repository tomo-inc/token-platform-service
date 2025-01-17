package com.tomo.model.req;

import lombok.Data;

@Data
public class OnchainTokenReq {
    Long chainId;
    String address;
}
