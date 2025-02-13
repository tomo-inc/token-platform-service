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

@Slf4j
@Component
public class TokenImageUrlConsumer {
    private static final String traceId = "traceId";
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FourMemeClient fourMemeClient;

    @KafkaListener(topics = "four-meme-token-image-topic", groupId = "token-group")
    public void listen(String msg) {
        log.info("token-image-topic msg "+msg);
        String[] split = msg.split("---");
        String tokenAddress = split[1];
        Long id = Long.parseLong(split[0]);
        Result<List<TokenInfoRes>> result = fourMemeClient.tokenQuery(tokenAddress, 1, 1);
        List<TokenInfoRes> dataList = result.getData();
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }
        TokenInfoRes tokenInfoRes = dataList.get(0);
        FourMemeToken fourMemeToken = new FourMemeToken();
        fourMemeToken.setId(id);
        fourMemeToken.setImageUrl(tokenInfoRes.getImage());
        tokenService.updateToken(fourMemeToken);
    }
}
