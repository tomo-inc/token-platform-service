package com.tomo.service.market;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tomo.model.market.MarketTokenSecurityInfo;

public interface MarketTokenSecurityJobService {

    void syncAllTokenSecurityInfo();

    void syncNullTokenSecurityInfo();
}
