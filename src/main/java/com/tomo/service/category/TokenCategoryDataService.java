package com.tomo.service.category;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tomo.model.ChainRegexEnum;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

public interface TokenCategoryDataService extends IService<TokenCategoryCoinGeckoDTO>{
    // 30分钟更新一次
    long updateTime = 30 * 60  * 1000;

    default boolean exists(String id) {
        LambdaQueryWrapper<TokenCategoryCoinGeckoDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenCategoryCoinGeckoDTO::getCoingeckoCoinId, id);
        return IService.super.exists(queryWrapper);
    }

    default List<TokenCategoryCoinGeckoDTO> longUnupdatedToken(boolean isNativeToken) {
        LambdaQueryWrapper<TokenCategoryCoinGeckoDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(wrapper ->
                        wrapper.le(TokenCategoryCoinGeckoDTO::getUpdateTime, System.currentTimeMillis() - updateTime)
                        .or()
                        .isNull(TokenCategoryCoinGeckoDTO::getUpdateTime))
                    .eq(TokenCategoryCoinGeckoDTO::getIsNative,isNativeToken)
                    .last("LIMIT 1500");
        return IService.super.list(queryWrapper);
    }

    @Override
    default boolean save(TokenCategoryCoinGeckoDTO entity) {
        if (StringUtils.hasText(entity.getAddress()) && ChainRegexEnum.isEVM(entity.getAddress())){
            entity.setAddress(entity.getAddress().toLowerCase());
        }
        return IService.super.save(entity);
    }

    @Override
    default boolean saveBatch(Collection<TokenCategoryCoinGeckoDTO> entityList) {
        entityList.forEach(entity -> {
            if (StringUtils.hasText(entity.getAddress()) && ChainRegexEnum.isEVM(entity.getAddress())){
                entity.setAddress(entity.getAddress().toLowerCase());
            }
        });
        return IService.super.saveBatch(entityList);
    }


    boolean insertOrUpdate(TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO);

    void batchInsertOrUpdate(List<TokenCategoryCoinGeckoDTO> tokenCategoryUpdateList);
}
