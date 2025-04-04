package com.tomo.service.market;

import com.tomo.model.market.GDexTxInfo;
import com.tomo.model.market.enums.GDexChainEnum;
import com.tomo.model.req.JsonRpcReq;
import com.tomo.model.resp.JsonRpcResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GDexClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${market.gdex.url}")
    private String url;

    @Value("${market.gdex.appId}")
    private String appId;

    public String getUrl(Long chainIndex) {
        GDexChainEnum gDexChainEnum = GDexChainEnum.getByChainIndex(chainIndex);
        if (gDexChainEnum == null) {
            return null;
        }
        return url+gDexChainEnum.getName()+"/"+appId;
    }



    public List<GDexTxInfo> dex_txsQuery(Long chainIndex, String address,Integer page, Integer size) {
        JsonRpcReq request = new JsonRpcReq();
        List<Object> params = new ArrayList<>();
        params.add(address);
        Map<String, Object> map = new HashMap<>();
//        map.put("target", "token/swap");
        map.put("page", page);
        map.put("size", size);
        params.add(map);
        request.setParams(params);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = getUrl(chainIndex);
        if (url == null) {
            log.info("GDexClientService#dex_txsQuery getUrl is null, chainIndex: {}", chainIndex);
            return new ArrayList<>();
        }
        HttpEntity<JsonRpcReq> entity = new HttpEntity<>(request, headers);
        JsonRpcResp<List<GDexTxInfo>> resp= restTemplate.postForObject(url, entity, JsonRpcResp.class);
        if (resp == null || CollectionUtils.isEmpty(resp.getResult())) {
            log.info("GDexClientService#dex_txsQuery resp is null, chainIndex: {}", chainIndex);
            return new ArrayList<>();
        }
        return resp.getResult();
    }

}
