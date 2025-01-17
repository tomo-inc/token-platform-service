package com.tomo.service.risk.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.BlackWhiteListMapper;
import com.tomo.model.dto.BlackWhiteListDTO;
import com.tomo.service.risk.BlackWhiteListService;
import org.springframework.stereotype.Service;
@Service
public class BlackWhiteListServiceImpl extends ServiceImpl<BlackWhiteListMapper, BlackWhiteListDTO> implements BlackWhiteListService {

    @Override
    public Boolean isBlackList(Long chainId, String address) {
        LambdaQueryWrapper<BlackWhiteListDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlackWhiteListDTO::getChainId, chainId)
                    .eq(BlackWhiteListDTO::getAddress, address);
        BlackWhiteListDTO blackWhiteListDTO = baseMapper.selectOne(queryWrapper);
        if (blackWhiteListDTO == null) {
            return null;
        }
        return blackWhiteListDTO.getType();
    }
}
