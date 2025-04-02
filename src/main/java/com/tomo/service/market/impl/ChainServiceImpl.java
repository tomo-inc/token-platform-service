package com.tomo.service.market.impl;

import com.tomo.annotation.Json;
import com.tomo.model.ChainPlatformType;
import com.tomo.model.dto.BackTokenDTO;
import com.tomo.model.dto.ChainDTO;
import com.tomo.model.dto.ChainEvmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChainServiceImpl {

    @Json("json/chains.json")
    private List<ChainDTO> chains;

    @Json("json/tokens.json")
    private List<BackTokenDTO> tokens;

    public List<ChainDTO> listAll() {
        return chains;
    }

    public ArrayList<ChainEvmDTO> listEvm(){
        var result = new ArrayList<ChainEvmDTO>();
        for(var chain : chains){
            if(chain.getPlatformType() == ChainPlatformType.EVM && chain.getRpcUrls() == null) continue;
            if(chain.getChainId() == null) continue;
            var chainEvmDTO = new ChainEvmDTO()
                    .setChainId(chain.getChainId())
                    .setName(chain.getName())
                    .setRpcUrls(chain.getRpcUrls())
                    .setBlockExplorerUrl(chain.getBlockExplorerUrl())
                    .setPlatformType(chain.getPlatformType())
                    .setIsTestnet(chain.getIsTestnet());

            var tokenDO = tokens.stream().filter(token -> token.getChain().equals(chain.getName()) && token.getIsNative()).findFirst();
            if(tokenDO.isPresent()){
                BackTokenDTO token = tokenDO.get();
                chainEvmDTO.setNativeCurrencyName(token.getDisplayName())
                           .setNativeCurrencySymbol(token.getSymbol())
                           .setNativeCurrencyDecimals(token.getDecimals())
                           .setIcon(token.getImageUrl());
            }
            result.add(chainEvmDTO);
        }
       return result;
    }

    public ChainDTO getChainByName(String name) {
        return chains.stream().filter(chain -> chain.getName().equals(name)).findFirst().orElse(null);
    }
}
