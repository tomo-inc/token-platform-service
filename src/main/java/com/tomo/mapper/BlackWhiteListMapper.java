package com.tomo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.BlackWhiteListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlackWhiteListMapper extends BaseMapper<BlackWhiteListDTO> {
}