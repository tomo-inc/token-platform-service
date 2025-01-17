package com.tomo.service.category;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tomo.model.dto.TokenInfoDTO;

import java.util.List;

public interface TokenInfoService extends IService<TokenInfoDTO>{
    boolean insertOrUpdate(TokenInfoDTO tokenInfoDTO);

    TokenInfoDTO exactQueryToken(Long chainId, String address);

    // 只能从本地模糊查询
    List<TokenInfoDTO> fuzzQueryPlatformToken(Long chainId, String addressOrName);

    static boolean needUpdate(long updateTime){
        return updateTime - System.currentTimeMillis() >= 10 * 1000;
    }
}
