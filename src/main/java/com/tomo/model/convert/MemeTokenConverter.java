package com.tomo.model.convert;

import com.tomo.model.dto.FourMemeToken;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.util.ConvertSupport;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper(uses = {
    ConvertSupport.class
},
    imports = {
        StringUtils.class,
    })
public interface MemeTokenConverter {
    MemeTokenConverter INSTANCE = Mappers.getMapper(MemeTokenConverter.class);

    MemeTokenDTO toTokenDto(FourMemeToken req);

}
