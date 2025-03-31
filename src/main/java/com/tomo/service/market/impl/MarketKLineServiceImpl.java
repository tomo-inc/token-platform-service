package com.tomo.service.market.impl;

import com.tomo.model.resp.MarketOHLCVInfo;
import com.tomo.service.market.MarketKLineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketKLineServiceImpl implements MarketKLineService {
    @Override
    public List<MarketOHLCVInfo> getKLine(Long chainIndex, String address, String interval) {

        return List.of();
    }
}
