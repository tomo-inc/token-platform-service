package com.tomo.kafka;


import cn.hutool.json.JSONUtil;
import com.tomo.kafka.message.TokenMessageDto;
import com.tomo.model.dto.FourMemeToken;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class FourMemeTokenConsumer {
    private static final String traceId = "traceId";
    @Autowired
    private TokenService tokenService;
    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @KafkaListener(topics = "four-meme-token-data-topic", groupId = "token-group")
    public void listen(String msg) {
        log.info("four meme token "+msg);
        List<TokenMessageDto> list = JSONUtil.toList(msg, TokenMessageDto.class);
        Date now = new Date();
        for (TokenMessageDto tokenMessageDto : list) {
            String tokenAddress = tokenMessageDto.getTokenAddress().toLowerCase();
            String priceChangeH24 = String.format("%.2f",Double.valueOf(tokenMessageDto.getPriceChangeH24()));
            String riseTokenAddress = StringUtils.equalsIgnoreCase(tokenMessageDto.getRiseTokenSymbol(),"BNB") ? "" : tokenMessageDto.getRiseTokenAddress().toLowerCase();
            String tokenName = tokenMessageDto.getName();
            try {
                FourMemeToken fourMemeToken = tokenService.querByAddress(tokenAddress);
                if (fourMemeToken != null) {
                    fourMemeToken.setRaiseTokenAddress(riseTokenAddress);
                    fourMemeToken.setRaiseTokenSymbol(tokenMessageDto.getRiseTokenSymbol());
                    fourMemeToken.setTokenName(tokenName);
                    fourMemeToken.setTokenSymbol(tokenMessageDto.getSymbol());
                    fourMemeToken.setTokenPrecision(tokenMessageDto.getDecimals());
                    fourMemeToken.setPriceUsd(tokenMessageDto.getPrice());
                    fourMemeToken.setPriceChangeH24(priceChangeH24);
                    fourMemeToken.setVolumeH24(tokenMessageDto.getVolumeH24());
                    fourMemeToken.setMarketCapUsd(tokenMessageDto.getMarketCapUsd());
                    fourMemeToken.setProgress(tokenMessageDto.getProgress());
                    fourMemeToken.setPublishTime(tokenMessageDto.getPublishTime());
                    fourMemeToken.setLaunchOnPancake(tokenMessageDto.getLaunchOnPancake());
                    fourMemeToken.setUpdateTime(now);
                    tokenService.updateToken(fourMemeToken);
                    if(StringUtils.isBlank(fourMemeToken.getImageUrl())){
                        kafkaTemplate.send("four-meme-token-image-topic", fourMemeToken.getId()+"---"+fourMemeToken.getTokenAddress());
                    }
                }else {
                    fourMemeToken = new FourMemeToken();
                    fourMemeToken.setRaiseTokenAddress(riseTokenAddress);
                    fourMemeToken.setRaiseTokenSymbol(tokenMessageDto.getRiseTokenSymbol());
                    fourMemeToken.setTokenAddress(tokenAddress);
                    fourMemeToken.setTokenName(tokenName);
                    fourMemeToken.setTokenSymbol(tokenMessageDto.getSymbol());
                    fourMemeToken.setTokenPrecision(tokenMessageDto.getDecimals());
                    fourMemeToken.setPriceUsd(tokenMessageDto.getPrice());
                    fourMemeToken.setPriceChangeH24(priceChangeH24);
                    fourMemeToken.setVolumeH24(tokenMessageDto.getVolumeH24());
                    fourMemeToken.setMarketCapUsd(tokenMessageDto.getMarketCapUsd());
                    fourMemeToken.setProgress(tokenMessageDto.getProgress());
                    fourMemeToken.setPublishTime(tokenMessageDto.getPublishTime());
                    fourMemeToken.setLaunchOnPancake(tokenMessageDto.getLaunchOnPancake());
                    tokenService.addToken(fourMemeToken);
                    kafkaTemplate.send("four-meme-token-image-topic", fourMemeToken.getId()+"---"+fourMemeToken.getTokenAddress());
                }
            }catch (Exception e){
                log.error("save or update token fail {}",JSONUtil.toJsonStr(tokenMessageDto));
                log.error("save or update token error", e);
            }
        }
    }
}
