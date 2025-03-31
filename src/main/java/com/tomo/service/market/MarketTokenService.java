package com.tomo.service.market;

import com.tomo.model.req.MarketTokenCategoryReq;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.model.resp.MarketTokenHistory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface MarketTokenService {

    List<MarketTokenBaseInfo> queryBaseInfoList(@NotBlank List<MarketTokenReq> list);

    List<MarketTokenDetailInfo> details(@Valid @NotEmpty List<MarketTokenReq> list);

    List<MarketTokenDetailInfo> category(@Valid @NotEmpty MarketTokenCategoryReq req);

    List<MarketTokenHistory> history(@NotNull Long chainIndex, String address);
}
