package com.tomo.service.market;

import com.tomo.model.resp.MarketOHLCVInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface MarketKLineService {

    List<MarketOHLCVInfo> getKLine(@NotNull Long chainIndex, String address, @NotBlank String interval);
}
