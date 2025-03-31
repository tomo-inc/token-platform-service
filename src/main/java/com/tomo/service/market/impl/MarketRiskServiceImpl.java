package com.tomo.service.market.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tomo.mapper.MarketTokenSecurityInfoMapper;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketRiskDetail;
import com.tomo.service.market.MarketRiskService;
import com.tomo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class MarketRiskServiceImpl implements MarketRiskService {

    @Autowired
    private MarketTokenSecurityInfoMapper marketTokenSecurityInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<MarketRiskDetail> list(List<MarketTokenReq> list) {
        List<MarketRiskDetail> result = new ArrayList<>();

        List<MarketTokenSecurityInfo> riskList = marketTokenSecurityInfoMapper.list(list);
        if (CollectionUtils.isEmpty(riskList)) {
            return result;
        }

        for (MarketTokenSecurityInfo info : riskList) {
            MarketRiskDetail detail = new MarketRiskDetail();
            MarketRiskDetail.Risk risk = new MarketRiskDetail.Risk();
            MarketRiskDetail.Warn warn = new MarketRiskDetail.Warn();

            setIfTrue(risk::setOwnerChangeBalance, info.getOwnerChangeBalance());
            setIfTrue(risk::setExternalCall, info.getExternalCall());
            setIfTrue(risk::setHiddenOwner, info.getHiddenOwner());
            setIfTrue(risk::setIsProxy, info.getIsProxy());
            setIfTrue(risk::setIsOpenSource, info.getIsOpenSource() == null ? null : !info.getIsOpenSource());
            if (info.getBalanceMutableAuthority() != null) {
                var json = parseStringToJson(info.getBalanceMutableAuthority());
                if ("1".equals(json.get("status").asText())) {
                    risk.setBalanceMutableAuthority(true);
                }
            }
            if (info.getCreator() != null) {
                var array = parseStringToJson(info.getCreator());
                if (array.isArray() && !array.isEmpty()) {
                    boolean hasMaliciousAddress = StreamSupport.stream(array.spliterator(), false)
                            .anyMatch(element -> "1".equals(element.get("malicious_address").asText()));
                    if (hasMaliciousAddress) {
                        risk.setMaliciousAddress(true);
                    }
                }
            }
            setIfTrue(risk::setIsHoneypot, info.getIsHoneypot());
            if (info.getBuyTax() != null) {
                if (info.getBuyTax().compareTo(BigDecimal.valueOf(0.5)) > 0) {
                    risk.setBuyTax(true);
                } else if (info.getBuyTax().compareTo(BigDecimal.valueOf(0.1)) >= 0 && info.getBuyTax().compareTo(BigDecimal.valueOf(0.5)) < 0) {
                    warn.setBuyTax(info.getBuyTax());
                }
            } else if (info.getIsOpenSource() != null) {
                warn.setBuyTax(BigDecimal.valueOf(0));
            }
            if (info.getSellTax() != null) {
                if (info.getSellTax().compareTo(BigDecimal.valueOf(0.5)) > 0) {
                    risk.setSellTax(true);
                } else if (info.getSellTax().compareTo(BigDecimal.valueOf(0.1)) >= 0 && info.getSellTax().compareTo(BigDecimal.valueOf(0.5)) < 0) {
                    warn.setSellTax(info.getSellTax());
                }
            } else if (info.getIsOpenSource() != null) {
                warn.setSellTax(BigDecimal.valueOf(0));
            }
            setIfTrue(warn::setCannotSellAll, info.getCannotSellAll());
            setIfTrue(warn::setSlippageModifiable, info.getSlippageModifiable());
            setIfTrue(warn::setPersonalSlippageModifiable, info.getPersonalSlippageModifiable());
            if (info.getTransferFee() != null) {
                var json = parseStringToJson(info.getTransferFee());
                JsonNode currentFeeRate = json.get("current_fee_rate");
                if (currentFeeRate != null) {
                    BigDecimal feeRate = currentFeeRate.get("fee_rate").decimalValue();
                    feeRate = feeRate.divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);
                    if (feeRate.compareTo(BigDecimal.valueOf(0.5)) > 0) {
                        risk.setFeeRate(true);
                    } else if (feeRate.compareTo(BigDecimal.valueOf(0.5)) < 0 &&
                            feeRate.compareTo(BigDecimal.valueOf(0.1)) >= 0) {
                        warn.setFeeRate(feeRate);
                    }
                }
            }
            if (info.getTransferFeeUpgradable() != null) {
                var json = parseStringToJson(info.getTransferFeeUpgradable());
                if ("1".equals(json.get("status").asText())) {
                    warn.setTransferFeeUpgradable(true);
                }
            }
            if (info.getClosable() != null) {
                var json = parseStringToJson(info.getClosable());
                if ("1".equals(json.get("status").asText())) {
                    warn.setIsClosable(true);
                }
            }
            if (info.getFreezable() != null) {
                var json = parseStringToJson(info.getFreezable());
                if ("1".equals(json.get("status").asText())) {
                    warn.setFreezable(true);
                }
            }
            if (info.getTransferHookUpgradable() != null) {
                var json = parseStringToJson(info.getTransferHookUpgradable());
                if ("1".equals(json.get("status").asText())) {
                    warn.setTransferHookUpgradable(true);
                }
            }
            detail.setRisk(risk);
            detail.setWarn(warn);
            result.add(detail);

        }

        return result;
    }

    private void setIfTrue(Consumer<Boolean> setter, Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            setter.accept(true);
        }
    }

    private JsonNode parseStringToJson(Map<String, Object> map) {
        try {
            String jsonString = JsonUtil.toJson(map);
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            if (jsonNode.isArray()) {
                return (ArrayNode) jsonNode;
            } else if (jsonNode.isObject()) {
                return (ObjectNode) jsonNode;
            } else {
                throw new RuntimeException("Provided string is not a valid JSON object or array: " + jsonString);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON string: " + map, e);
        }
    }
}
