package com.tomo.kafka;


import cn.hutool.json.JSONUtil;
import com.tomo.feign.FourMemeClient;
import com.tomo.kafka.message.TokenMessageDto;
import com.tomo.model.dto.FourMemeToken;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.Result;
import com.tomo.model.resp.TokenInfoRes;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class TokenImageUrlConsumer {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FourMemeClient fourMemeClient;

    @KafkaListener(topics = "four-meme-token-image-topic", groupId = "token-group")
    public void listen(String msg) {
        String traceId = UUID.randomUUID().toString();
        try {
            log.info(traceId+"token-image-topic msg "+msg);
            String[] split = msg.split("---");
            String tokenAddress = split[1];
            Long id = Long.parseLong(split[0]);
            Result<TokenInfoRes> result = fourMemeClient.getToken(tokenAddress);
            TokenInfoRes tokenInfoRes = result.getData();
            if(Objects.isNull(tokenInfoRes)){
                return;
            }
            FourMemeToken fourMemeToken = new FourMemeToken();
            fourMemeToken.setId(id);
            fourMemeToken.setImageUrl(tokenInfoRes.getImage());
            tokenService.updateToken(fourMemeToken);
        }catch (Exception e){
            log.error(traceId+"fetch token image fail",e);
        }

    }
}
