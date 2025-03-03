package com.tomo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.resp.OnChainTokenInfo;
import com.tomo.model.resp.OnChainTokenPrice;
import com.tomo.service.category.impl.CoinGeckoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class TokenInfoCacheUpdater {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CoinGeckoServiceImpl coinGeckoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${cache.key.token.info.prefix}")
    private String REDIS_TOKEN_INFO_PREFIX;

    private static final String LOCK_KEY = "TPS_LOCK";
    private static final long LOCK_EXPIRE_TIME_MS = 3600;

    @Scheduled(fixedRate = 20 * 60 * 1000)
//    @Scheduled(fixedRate = 20 * 60 * 1000, initialDelay = 20 * 60 * 1000)
    public void runTask() {
        String lockId = UUID.randomUUID().toString();
        log.info("TokenInfoCacheUpdater start, lockId: {}", lockId);
        try {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY, lockId, LOCK_EXPIRE_TIME_MS, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(success)) {
                log.info("TokenInfoCacheUpdater lock failed, lockId: {}", lockId);
                return;
            }
            log.info("TokenInfoCacheUpdater lock success, lockId: {}", lockId);

            List<OnChainTokenInfo> allCachedTokenInfo = getAllCachedTokenInfo();
            List<List<OnchainTokenReq>> partitioned = ListUtils.partition(allCachedTokenInfo.stream().map(info -> new OnchainTokenReq(info.getChainId(), info.getTokenContractAddress())).toList(), 1000);

            log.info("TokenInfoCacheUpdater, allCachedTokenInfo size = {}, partitioned size = {}", allCachedTokenInfo.size(), partitioned.size());
            partitioned.forEach(reqs -> {
                Pair<List<OnChainTokenInfo>, List<OnChainTokenPrice>> pair = coinGeckoService.fetchOnChainTokenInfoAndPrice(reqs);
                coinGeckoService.updateTokenInfoAndPriceCache(pair);
                log.info("TokenInfoCacheUpdater, updated token info and price, request size = {}, info size = {}", reqs.size(), pair.getFirst().size());
                // sleep 1s to avoid rate limit ?
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("TokenInfoCacheUpdater Thread sleep error", e);
                }
            });

            log.info("TokenInfoCacheUpdater finished! lockId: {}", lockId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            String luaScript =
                    "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                            "   return redis.call('del', KEYS[1]) " +
                            "else " +
                            "   return 0 " +
                            "end";
            redisTemplate.execute(redisScript(luaScript), List.of(LOCK_KEY), lockId);
            log.info("TokenInfoCacheUpdater unlocked! lockId: {}", lockId);
        }
    }

    private List<OnChainTokenInfo> getAllCachedTokenInfo() {
        List<String> keys = new ArrayList<>();
        List<Object> tokenInfos = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions().match(REDIS_TOKEN_INFO_PREFIX + "*").build();
        Cursor<String> cursor = redisTemplate.scan(options);
        while (cursor.hasNext()) {
            String key = cursor.next();
            keys.add(key);
        }
        cursor.close();
        if (!keys.isEmpty()) {
            tokenInfos = redisTemplate.opsForValue().multiGet(keys);
        }
        if (tokenInfos == null) {
            return new ArrayList<>();
        }
        return tokenInfos.stream().map(obj -> objectMapper.convertValue(obj, OnChainTokenInfo.class)).sorted((o1, o2) -> (int) (o1.getChainId() - o2.getChainId())).toList();
    }

    private DefaultRedisScript<Long> redisScript(String script) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
