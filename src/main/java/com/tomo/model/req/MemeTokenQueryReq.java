package com.tomo.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemeTokenQueryReq {
    private String status;
    private Boolean launchOnPancake;

}
