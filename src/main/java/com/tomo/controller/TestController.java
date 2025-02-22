package com.tomo.controller;

import com.tomo.feign.FourMemeClient;
import com.tomo.model.ResultUtils;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.Result;
import com.tomo.model.resp.TokenInfoRes;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private FourMemeClient fourMemeClient;

    @GetMapping("four/meme/image")
    public Result<List<TokenInfoRes>> tokenImage(@RequestParam String tokenAddress) {
        try {
            Result<List<TokenInfoRes>> result = fourMemeClient.tokenQuery(tokenAddress, 1, 1);

            return ResultUtils.success(result.getData());
        }catch (Exception e){
            log.info(tokenAddress+" "+getStackTrace(e));
            log.error("token query fail",e);
        }
        return null;
    }
    private String getStackTrace(Exception ex) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        int depth = stackTrace.length;
        for (int i = 0; i < depth; i++) {
            sb.append(stackTrace[i].toString()).append("\n");
        }
        return sb.toString();
    }

}
