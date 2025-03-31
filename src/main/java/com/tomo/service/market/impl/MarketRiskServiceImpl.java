package com.tomo.service.market.impl;

import com.tomo.mapper.MarketTokenSecurityInfoMapper;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketRiskDetail;
import com.tomo.service.market.MarketRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MarketRiskServiceImpl implements MarketRiskService {

    @Autowired
    private MarketTokenSecurityInfoMapper marketTokenSecurityInfoMapper;

    @Override
    public List<MarketRiskDetail> list(List<MarketTokenReq> list) {

        List<MarketTokenSecurityInfo> riskList = marketTokenSecurityInfoMapper.list(list);

        for (MarketTokenSecurityInfo info : riskList) {
//            MarketRiskDetail detail = new MarketRiskDetail();
//            MarketRiskDetail.Risk risk = new MarketRiskDetail.Risk();
//            risk.setOwnerChangeBalance(info.getOwnerChangeBalance());
//            risk.setExternalCall(info.getExternalCall());
//            risk.setHiddenOwner(info.getHiddenOwner());
//            risk.setProxy(info.getIsProxy());
//            risk.setOpenSource(info.getIsOpenSource());
//            risk.setBalanceMutableAuthority(info.getBalanceMutableAuthority());
//            risk.setTransferHook();
//            risk.setMaliciousAddress();
//            risk.setHoneypot();
//            risk.setBuyTax();
//            risk.setSellTax();
//            risk.setNonTransferable();
//            risk.setFeeRate();
//
//            detail.setRisk(risk);
//
//
//            MarketRiskDetail.Warn warn = new MarketRiskDetail.Warn();
//            warn.setBuyTax();
//            warn.setSellTax();
//            warn.setCannotSellAll();
//            warn.setSlippageModifiable();
//            warn.setPersonalSlippageModifiable();
//            warn.setFeeRate();
//            warn.setTransferFeeUpgradable();
//            warn.setTransferPausable();
//            warn.setTradingCooldown();
//            warn.setBlacklisted();
//            warn.setWhitelisted();
//            warn.setClosable();
//            warn.setFreezable();
//            warn.setTransferHookUpgradable();
//
//            detail.setWarn(warn);


        }

        return List.of();
    }
}
