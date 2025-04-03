package com.tomo.mapper;

import com.tomo.model.MarketSubscribeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarketSubscribeInfoMapper {

    MarketSubscribeInfo selectById(Long id);

    List<MarketSubscribeInfo> selectAll();

    void insert(MarketSubscribeInfo marketSubscribeInfo);

    void update(MarketSubscribeInfo marketSubscribeInfo);

    void delete(Long id);

    void batchInsert(List<MarketSubscribeInfo> list);

    void batchUpdate(List<MarketSubscribeInfo> list);

    List<MarketSubscribeInfo> pageList(Long chainIndex, String address, Integer offset, Integer pageSize);
}