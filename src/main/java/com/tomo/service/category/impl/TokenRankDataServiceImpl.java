package com.tomo.service.category.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.TokenRankMapper;
import com.tomo.model.dto.TokenRankDTO;
import com.tomo.service.category.TokenRankDataService;
import org.springframework.stereotype.Service;

@Service
public class TokenRankDataServiceImpl extends ServiceImpl<TokenRankMapper, TokenRankDTO> implements TokenRankDataService {

}
