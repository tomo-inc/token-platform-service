package com.tomo.model.resp;

import lombok.Data;

@Data
public class MarketOHLCVInfo {
    private Long timestamp;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;

}
