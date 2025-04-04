package com.tomo.service.price;


import com.tomo.model.market.enums.IntervalEnum;
import com.tomo.model.dto.TokenOhlcvDTO;

import java.util.List;

public interface KLineService {
//    List<SolKlineDTO> getSolTokenKline(String address, IntervalEnum interval);

    List<TokenOhlcvDTO> getTokenOhlcv(Long chainId, String address, IntervalEnum interval);
}
