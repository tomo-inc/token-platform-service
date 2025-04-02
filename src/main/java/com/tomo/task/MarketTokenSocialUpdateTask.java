package com.tomo.task;

import com.tomo.service.market.MarketTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketTokenSocialUpdateTask {

    @Autowired
    private MarketTokenService marketTokenService;

    public void runTask() {
        marketTokenService.updateSocialInfo();
    }
}
