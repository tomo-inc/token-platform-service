package com.tomo.service.market;

import com.tomo.kafka.message.PriceKafkaMessage;

import java.util.List;

public interface MarketTokenPriceService {

    void handlePriceMsg(List<PriceKafkaMessage> messages);
}
