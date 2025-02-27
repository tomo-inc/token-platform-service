package com.tomo.grpc;

import chain_quote_indexer.ChainQuoteIndexerGrpc;
import chain_quote_indexer.ChainQuoteIndexerOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ChainQuoteIndexerClient {

    private final ChainQuoteIndexerGrpc.ChainQuoteIndexerBlockingStub blockingStub;

    public ChainQuoteIndexerClient(ManagedChannel channel) {
        this.blockingStub = ChainQuoteIndexerGrpc.newBlockingStub(channel);
    }

    public Map<String, ChainQuoteIndexerOuterClass.Quote> findQuotes(Long chainIndex, List<String> addrs) {
        // 构建请求
        ChainQuoteIndexerOuterClass.QuoteRequest request = ChainQuoteIndexerOuterClass.QuoteRequest.newBuilder()
                .setChainId(chainIndex)
                .addAllAddrs(addrs)
                .build();

        try {
            // 设置超时时间为 0.5 分钟
            long timeout = 30;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            // 为 Stub 对象设置截止时间
            ChainQuoteIndexerGrpc.ChainQuoteIndexerBlockingStub timedStub = blockingStub.withDeadlineAfter(timeout, timeUnit);
            // 调用 gRPC 服务
            log.info("gRPC api: findQuotes, requst: {}", request);
            ChainQuoteIndexerOuterClass.MapQuote quotes = timedStub.findQuotes(request);
            log.info("gRPC api: findQuotes，result: {}", quotes);

            return quotes.getItemsMap();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == io.grpc.Status.Code.DEADLINE_EXCEEDED) {
                log.error("gRPC time out: {}", e.getStatus(), e);
                return null;
            }
            log.error("gRPC api failed: {}", e.getStatus(), e);
            return null;
        }
    }
}
