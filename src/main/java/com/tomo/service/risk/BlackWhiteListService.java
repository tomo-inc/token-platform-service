package com.tomo.service.risk;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tomo.model.dto.BlackWhiteListDTO;

import java.util.List;

public interface BlackWhiteListService extends IService<BlackWhiteListDTO>{

    Boolean isBlackList(Long chainId, String address);

    @Override
    default boolean save(BlackWhiteListDTO entity) {
        return IService.super.save(entity);
    }

    @Override
    default boolean remove(Wrapper<BlackWhiteListDTO> queryWrapper) {
        return IService.super.remove(queryWrapper);
    }

    @Override
    default boolean update(Wrapper<BlackWhiteListDTO> updateWrapper) {
        return IService.super.update(updateWrapper);
    }

    @Override
    default List<BlackWhiteListDTO> list() {
        return IService.super.list();
    }


}
