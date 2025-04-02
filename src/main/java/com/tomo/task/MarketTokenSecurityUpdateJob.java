package com.tomo.task;


import com.tomo.service.market.MarketTokenSecurityJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MarketTokenSecurityUpdateJob {

    @Autowired
    private MarketTokenSecurityJobService jobService;

    public void syncAllTokenRisk(){
        log.info("syncAllTokenRisk start");
        try{
            jobService.syncAllTokenSecurityInfo();
        }catch (Exception e) {
            log.error("syncAllTokenRisk error: ", e);
        }
        log.info("syncAllTokenRisk end");
    }

    public void syncNullTokenRisk(){
        try{
            jobService.syncNullTokenSecurityInfo();
        }catch (Exception e) {
            log.error("syncNullTokenSecurityInfo error: ", e);
        }
    }
}
