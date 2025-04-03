package com.tomo.kafka;

import cn.hutool.json.JSONUtil;
import com.tomo.kafka.message.PriceKafkaMessage;
import com.tomo.service.market.MarketTokenPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PriceKafkaConsumer {

    @Autowired
    private MarketTokenPriceService marketTokenPriceService;

    // TODO topic待提供
    @KafkaListener(topics = "price-topic", groupId = "token-group")
    public void listen(String msg) {
        log.info("price kafka consumer "+msg);
        List<PriceKafkaMessage> list = JSONUtil.toList(msg, PriceKafkaMessage.class);

        marketTokenPriceService.handlePriceMsg(list);
    }
}
