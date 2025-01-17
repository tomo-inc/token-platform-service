package com.tomo.service.price;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tomo.feign.BitqueryClient;
import com.tomo.model.dto.PoolDTO;
import com.tomo.model.dto.SolKlineDTO;
import com.tomo.model.dto.TradeDTO;
import com.tomo.model.req.BitqueryReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BitqueryService {

    final private BitqueryClient bitqueryClient;

    public List<SolKlineDTO> getSolanaKline(String base, String pair, Integer interval, String intervalUnit, Integer limit) {
        String query = """
          query ($dataset: dataset_arg_enum, $time_ago: DateTime, $interval: Int, $base: String, $pair: String, $intervalUnit: OLAP_DateTimeIntervalUnits, $limit: Int) {
             Solana(dataset: $dataset) {
               DEXTradeByTokens(
                 orderBy: {descendingByField: "Block_Time"}
                 where: {Block: {Time: {after: $time_ago}}, Trade: {Side: {Amount: {gt: "0"}, Currency: {MintAddress: {in: ["", "So11111111111111111111111111111111111111112", "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v", "Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB", "JUPyiwrYJFskUPiHa7hkeR8VUtAeFoSYbKedZNsDvCN", "EKpQGSJtjMFqKZ9KQanSqYXRcF8fBopzLHYxdM65zcjm"]}}}, Currency: {MintAddress: {is: $base}}, Market: {MarketAddress: {is: $pair}}}}
                 limit: {count: $limit}
               ) {
                 Block {
                   Time(interval: {count: $interval, in: $intervalUnit})
                 }
                 Trade{
                   min: PriceInUSD(minimum: Trade_PriceInUSD)
                   max: PriceInUSD(maximum: Trade_PriceInUSD)
                   close: PriceInUSD(maximum: Block_Slot)
                   open: PriceInUSD(minimum: Block_Slot)
                 }
                 volume: sum(of: Trade_Side_AmountInUSD)
               }
             }
           }
          """;
        Map<String, Object> variables = new HashMap<>();
        variables.put("base", base);
        variables.put("pair", pair);
        variables.put("interval", interval);
        variables.put("intervalUnit", intervalUnit);
        variables.put("limit", limit);
        variables.put("dataset", "combined");

        BitqueryReq request = new BitqueryReq().setQuery(query).setVariables(variables);
        Map<String, Object> data = bitqueryClient.query(request);
        DocumentContext docData = JsonPath.parse(data);
        List<Map> dexTradeByTokens = docData.read("$['data']['Solana']['DEXTradeByTokens']", List.class);
        return dexTradeByTokens.stream().map((dexTradeByToken) -> {
            DocumentContext doc = JsonPath.parse(dexTradeByToken);
            Double max = doc.read("$['Trade']['max']", Double.class);
            Double min = doc.read("$['Trade']['min']", Double.class);
            Double open = doc.read("$['Trade']['open']", Double.class);
            Double close = doc.read("$['Trade']['close']", Double.class);
            Double volume = doc.read("$['volume']", Double.class);
            String timeStr = doc.read("$['Block']['Time']", String.class);
            Long time = OffsetDateTime.parse(timeStr).toEpochSecond();
            return new SolKlineDTO()
                    .setHigh(max)
                    .setLow(min)
                    .setOpen(open)
                    .setClose(close)
                    .setVolume(volume)
                    .setTime(time);
        }).collect(Collectors.collectingAndThen(
                Collectors.toList(),
                collectedList -> {
                    Collections.reverse(collectedList);
                    return collectedList;
                }
        ));
    }

    public List<TradeDTO> getSolanaLatestTrades(String base, Integer limit) {
        String query = """
          query LatestTrades($base: String, $limit: Int) {
            Solana(dataset: realtime) {
              DEXTradeByTokens(
                orderBy: {descending: Block_Time}
                where: {Transaction: {Result: {Success: true}}, Trade: {Side: {Amount: {gt: "0"}, Currency: {MintAddress: {in: ["", "So11111111111111111111111111111111111111112", "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v", "Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB", "JUPyiwrYJFskUPiHa7hkeR8VUtAeFoSYbKedZNsDvCN", "EKpQGSJtjMFqKZ9KQanSqYXRcF8fBopzLHYxdM65zcjm"]}}}, Currency: {MintAddress: {is: $base}}}}
                limit: {count: $limit}
              ) {
                Block {
                  time: Time
                }
                Transaction {
                  Signature
                }
                Trade {
                  Market {
                    MarketAddress
                  }
                  Currency {
                    Symbol
                    MintAddress
                  }
                  Account {
                    Owner
                  }
                  Dex {
                    ProtocolName
                    ProtocolFamily
                  }
                  Price
                  PriceInUSD
                  Amount
                  AmountInUSD
                  Side {
                    Type
                    Currency {
                      Symbol
                      MintAddress
                    }
                    Amount
                    AmountInUSD
                  }
                }
              }
            }
          }
          """;
        Map<String, Object> variables = new HashMap<>();
        variables.put("base", base);
        variables.put("limit", limit);
        BitqueryReq request = new BitqueryReq()
                .setQuery(query)
                .setVariables(variables);
        Map<String, Object> data = bitqueryClient.query(request);
        DocumentContext docData = JsonPath.parse(data);
        List<Map> dexTradeByTokens = docData.read("$['data']['Solana']['DEXTradeByTokens']", List.class);
        return dexTradeByTokens.stream().map((dexTradeByToken) -> {
            DocumentContext doc = JsonPath.parse(dexTradeByToken);
            String tokenAddress = doc.read("$['Trade']['Currency']['MintAddress']", String.class);
            String tokenOutAddress = doc.read("$['Trade']['Side']['Currency']['MintAddress']", String.class);
            String pair = doc.read("$['Trade']['Market']['MarketAddress']", String.class);
            String maker = doc.read("$['Trade']['Account']['Owner']", String.class);
            String amount = doc.read("$['Trade']['Amount']", String.class);
            String amountOut = doc.read("$['Trade']['Side']['Amount']", String.class);
            String amountInUsd = doc.read("$['Trade']['AmountInUSD']", String.class);
            String mountOutInUsd = doc.read("$['Trade']['Side']['AmountInUSD']", String.class);
            String tokenSymbol = doc.read("$['Trade']['Currency']['Symbol']", String.class);
            String tokenOutSymbol = doc.read("$['Trade']['Side']['Currency']['Symbol']", String.class);
            String timeStr = doc.read("$['Block']['time']", String.class);
            String signature = doc.read("$['Transaction']['Signature']", String.class);
            Double price = Double.valueOf(doc.read("$['Trade']['Price']", String.class));
            Double priceInUsd = Double.valueOf(doc.read("$['Trade']['PriceInUSD']", String.class));
            String tradeType = doc.read("$['Trade']['Side']['Type']", String.class);
            TradeDTO.TradeType type = Objects.equals("buy", tradeType)
                    ? TradeDTO.TradeType.buy : TradeDTO.TradeType.sell;
            Long time = OffsetDateTime.parse(timeStr).toEpochSecond();
            return new TradeDTO()
                    .setPair(pair)
                    .setTokenAddress(tokenAddress)
                    .setTokenOutAddress(tokenOutAddress)
                    .setSignature(signature)
                    .setMaker(maker)
                    .setAmount(amount)
                    .setAmountOut(amountOut)
                    .setAmountInUsd(amountInUsd)
                    .setAmountOutInUsd(mountOutInUsd)
                    .setTokenSymbol(tokenSymbol)
                    .setTokenOutSymbol(tokenOutSymbol)
                    .setPrice(price)
                    .setPriceInUsd(priceInUsd)
                    .setTime(time)
                    .setType(type);
        }).toList();
    }

    public List<PoolDTO> getSolanaPools(String token) {
        String query = """
          query ($token: String) {
             Solana {
               DEXPools(
                 orderBy: {descendingByField: "Pool_Quote_PostAmountInUSD_maximum"}
                 where: {Pool: {Market: {BaseCurrency: {MintAddress: {is: $token}}}}}
               ) {
                 Pool {
                   Market {
                     QuoteCurrency {
                       Symbol
                       Name
                       MintAddress
                     }
                     MarketAddress
                     BaseCurrency {
                       Symbol
                       Name
                       MintAddress
                     }
                   }
                   Dex {
                     ProtocolFamily
                   }
                   Base {
                     PostAmount(maximum: Block_Slot)
                     PostAmountInUSD(maximum: Block_Slot)
                   }
                   Quote {
                     PostAmount(maximum: Block_Slot)
                     PostAmountInUSD(maximum: Block_Slot)
                   }
                 }
               }
             }
           }
          """;
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", token);
        BitqueryReq request = new BitqueryReq()
                .setQuery(query)
                .setVariables(variables);
        Map<String, Object> data = bitqueryClient.query(request);
        DocumentContext docData = JsonPath.parse(data);
        List<Map> pools = docData.read("$['data']['Solana']['DEXPools']", List.class);
        return pools.stream().map((pool) -> {
            DocumentContext doc = JsonPath.parse(pool);
            String address = doc.read("$['Pool']['Market']['MarketAddress']", String.class);
            String protocalFamily = doc.read("$['Pool']['Dex']['ProtocolFamily']", String.class);
            String baseTokenAddress = doc.read("$['Pool']['Market']['BaseCurrency']['MintAddress']", String.class);
            String quoteTokenAddress = doc.read("$['Pool']['Market']['QuoteCurrency']['MintAddress']", String.class);
            String baseTokenAmount = doc.read("$['Pool']['Base']['PostAmount']", String.class);
            String baseTokenAmountInUsd = doc.read("$['Pool']['Base']['PostAmountInUSD']", String.class);
            String quoteTokenAmount = doc.read("$['Pool']['Quote']['PostAmount']", String.class);
            String quoteTokenAmountInUsd = doc.read("$['Pool']['Quote']['PostAmountInUSD']", String.class);
            return new PoolDTO()
                    .setAddress(address)
                    .setProtocolFamily(protocalFamily)
                    .setBaseTokenAddress(baseTokenAddress)
                    .setQuoteTokenAddress(quoteTokenAddress)
                    .setBaseTokenAmount(new BigDecimal(baseTokenAmount))
                    .setBaseTokenAmountInUsd(new BigDecimal(baseTokenAmountInUsd))
                    .setQuoteTokenAmount(new BigDecimal(quoteTokenAmount))
                    .setQuoteTokenAmountInUsd(new BigDecimal(quoteTokenAmountInUsd));
        }).toList();
    }
}
