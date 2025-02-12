package com.tomo.kafka;


import cn.hutool.json.JSONUtil;
import com.tomo.kafka.message.TokenMessageDto;
import com.tomo.model.dto.FourMemeToken;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
        for (TokenMessageDto tokenMessageDto : list) {
            String tokenAddress = tokenMessageDto.getTokenAddress().toLowerCase();
            String riseTokenAddress = tokenMessageDto.getRiseTokenAddress().toLowerCase();
            String priceChangeH24 = String.format("%.2f",Double.valueOf(tokenMessageDto.getPriceChangeH24()));

            try {
                FourMemeToken fourMemeToken = tokenService.querByAddress(tokenAddress);
                if (fourMemeToken != null) {
                    fourMemeToken.setRiskTokenAddress(riseTokenAddress);
                    fourMemeToken.setRiskTokenSymbol(tokenMessageDto.getRiseTokenSymbol());
                    fourMemeToken.setTokenName(tokenMessageDto.getName());
                    fourMemeToken.setTokenSymbol(tokenMessageDto.getSymbol());
                    fourMemeToken.setTokenPrecision(tokenMessageDto.getDecimals());
                    fourMemeToken.setPriceUsd(tokenMessageDto.getPrice());
                    fourMemeToken.setPriceChangeH24(priceChangeH24);
                    fourMemeToken.setVolumeH24(tokenMessageDto.getVolumeH24());
                    fourMemeToken.setMarketCapUsd(tokenMessageDto.getMarketCapUsd());
                    tokenService.updateToken(fourMemeToken);
                }else {
                    fourMemeToken = new FourMemeToken();
                    fourMemeToken.setRiskTokenAddress(riseTokenAddress);
                    fourMemeToken.setRiskTokenSymbol(tokenMessageDto.getRiseTokenSymbol());
                    fourMemeToken.setTokenAddress(tokenAddress);
                    fourMemeToken.setTokenName(tokenMessageDto.getName());
                    fourMemeToken.setTokenSymbol(tokenMessageDto.getSymbol());
                    fourMemeToken.setTokenPrecision(tokenMessageDto.getDecimals());
                    fourMemeToken.setPriceUsd(tokenMessageDto.getPrice());
                    fourMemeToken.setPriceChangeH24(priceChangeH24);
                    fourMemeToken.setVolumeH24(tokenMessageDto.getVolumeH24());
                    fourMemeToken.setMarketCapUsd(tokenMessageDto.getMarketCapUsd());
                    tokenService.addToken(fourMemeToken);
                    kafkaTemplate.send("four-meme-token-image-topic", fourMemeToken.getId()+"---"+fourMemeToken.getTokenAddress());
                }
            }catch (Exception e){
                log.error("save or update token error", e);
            }
        }
    }
}
