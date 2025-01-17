package com.tomo.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenOhlcvDTO {
    private Long timestamp;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
}