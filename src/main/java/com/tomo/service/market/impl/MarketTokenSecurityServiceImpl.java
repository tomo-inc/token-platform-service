package com.tomo.service.market.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.MarketTokenSecurityInfoMapper;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.service.market.MarketTokenSecurityService;
import org.springframework.stereotype.Service;

@Service
public class MarketTokenSecurityServiceImpl extends ServiceImpl<MarketTokenSecurityInfoMapper, MarketTokenSecurityInfo> implements MarketTokenSecurityService {
}
