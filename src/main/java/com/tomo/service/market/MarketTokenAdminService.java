package com.tomo.service.market;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tomo.model.dto.MarketTokenDTO;
import com.tomo.model.req.MarketTokenQueryReq;

/**
 * 市场代币管理服务
 */
public interface MarketTokenAdminService {

    /**
     * 更新市场代币信息
     *
     * @param tokenDTO 代币信息
     */
    void updateMarketToken(MarketTokenDTO tokenDTO);

    /**
     * 获取市场代币列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<MarketTokenDTO> pageMarketToken(MarketTokenQueryReq queryDTO);
}
