package com.tomo.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomo.model.resp.SolanaSecurityResp;
import io.gopluslabs.client.model.ResponseWrapperTokenSecurityResult;

import java.math.BigDecimal;

public class GoplusResultConvert {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TokenRiskGoplusDTO covert(ResponseWrapperTokenSecurityResult tokenSecurityResult) {
        TokenRiskGoplusDTO tokenRiskGoplusDTO = new TokenRiskGoplusDTO();
        tokenRiskGoplusDTO.setIsOpenSource("1".equals(tokenSecurityResult.getIsOpenSource()));
        tokenRiskGoplusDTO.setIsProxy("1".equals(tokenSecurityResult.getIsProxy()));
        tokenRiskGoplusDTO.setIsMintable("1".equals(tokenSecurityResult.getIsMintable()));
        tokenRiskGoplusDTO.setOwnerAddress(tokenSecurityResult.getOwnerAddress());
        tokenRiskGoplusDTO.setCanTakeBackOwnership("1".equals(tokenSecurityResult.getCanTakeBackOwnership()));
        tokenRiskGoplusDTO.setOwnerChangeBalance("1".equals(tokenSecurityResult.getOwnerChangeBalance()));
        tokenRiskGoplusDTO.setHiddenOwner("1".equals(tokenSecurityResult.getHiddenOwner()));
        tokenRiskGoplusDTO.setSelfdestruct("1".equals(tokenSecurityResult.getSelfdestruct()));
        tokenRiskGoplusDTO.setExternalCall("1".equals(tokenSecurityResult.getExternalCall()));
        tokenRiskGoplusDTO.setIsInDex("1".equals(tokenSecurityResult.getIsInDex()));
        tokenRiskGoplusDTO.setCannotBuy("1".equals(tokenSecurityResult.getCannotBuy()));
        tokenRiskGoplusDTO.setCannotSellAll("1".equals(tokenSecurityResult.getCannotSellAll()));
        tokenRiskGoplusDTO.setSlippageModifiable("1".equals(tokenSecurityResult.getSlippageModifiable()));
        tokenRiskGoplusDTO.setIsTrueToken(!"0".equals(tokenSecurityResult.getIsTrueToken()));
        tokenRiskGoplusDTO.setIsHoneypot("1".equals(tokenSecurityResult.getIsHoneypot()));
        tokenRiskGoplusDTO.setTransferPausable("1".equals(tokenSecurityResult.getTransferPausable()));
        tokenRiskGoplusDTO.setIsBlacklisted("1".equals(tokenSecurityResult.getIsBlacklisted()));
        tokenRiskGoplusDTO.setIsWhitelisted("1".equals(tokenSecurityResult.getIsWhitelisted()));
        tokenRiskGoplusDTO.setIsAntiWhale("1".equals(tokenSecurityResult.getIsAntiWhale()));
        tokenRiskGoplusDTO.setAntiWhaleModifiable("1".equals(tokenSecurityResult.getAntiWhaleModifiable()));
        tokenRiskGoplusDTO.setTradingCooldown("1".equals(tokenSecurityResult.getTradingCooldown()));
        tokenRiskGoplusDTO.setPersonalSlippageModifiable("1".equals(tokenSecurityResult.getPersonalSlippageModifiable()));
        tokenRiskGoplusDTO.setCreatorAddress(tokenSecurityResult.getCreatorAddress());
        tokenRiskGoplusDTO.setIsAirdropScam("1".equals(tokenSecurityResult.getIsAirdropScam()));
        tokenRiskGoplusDTO.setTrustList("1".equals(tokenSecurityResult.getTrustList()));
        tokenRiskGoplusDTO.setOtherPotentialRisks(tokenSecurityResult.getOtherPotentialRisks());
        tokenRiskGoplusDTO.setTotalSupply(tokenSecurityResult.getTotalSupply());
        tokenRiskGoplusDTO.setNote(tokenSecurityResult.getNote());
        tokenRiskGoplusDTO.setOwnerBalance(tokenSecurityResult.getOwnerBalance());
        tokenRiskGoplusDTO.setOwnerPercent(tokenSecurityResult.getOwnerPercent());
        tokenRiskGoplusDTO.setCreatorPercent(tokenSecurityResult.getCreatorPercent());
        tokenRiskGoplusDTO.setLpTotalSupply(tokenSecurityResult.getLpTotalSupply());
        tokenRiskGoplusDTO.setCreatorBalance(tokenSecurityResult.getCreatorBalance());

        if (tokenSecurityResult.getHolders() != null) {
            tokenRiskGoplusDTO.setHolders(convertToJson(tokenSecurityResult.getHolders()));
        }
        if (tokenSecurityResult.getDex() != null) {
            tokenRiskGoplusDTO.setDex(convertToJson(tokenSecurityResult.getDex()));
        }
        if (tokenSecurityResult.getFakeToken() != null) {
            tokenRiskGoplusDTO.setFakeToken(convertToJson(tokenSecurityResult.getFakeToken()));
        }
        if (tokenSecurityResult.getLpHolders() != null) {
            tokenRiskGoplusDTO.setLpHolders(convertToJson(tokenSecurityResult.getLpHolders()));
        }
        if (tokenSecurityResult.getLpHolderCount() != null) {
            tokenRiskGoplusDTO.setLpHolderCount(Integer.parseInt(tokenSecurityResult.getLpHolderCount()));
        }
        if (tokenSecurityResult.getHolderCount() != null) {
            tokenRiskGoplusDTO.setHolderCount(Integer.parseInt(tokenSecurityResult.getHolderCount()));
        }
        if (tokenSecurityResult.getBuyTax() != null && !tokenSecurityResult.getBuyTax().trim().isEmpty()) {
            tokenRiskGoplusDTO.setBuyTax(new BigDecimal(tokenSecurityResult.getBuyTax()));
        }
        if (tokenSecurityResult.getSellTax() != null && !tokenSecurityResult.getSellTax().trim().isEmpty()) {
            tokenRiskGoplusDTO.setSellTax(new BigDecimal(tokenSecurityResult.getSellTax()));
        }


        return tokenRiskGoplusDTO;
    }

    public static TokenRiskGoplusDTO covert(SolanaSecurityResp.SolanaTokenDetails solanaTokenDetails){
        TokenRiskGoplusDTO tokenRiskGoplusDTO = new TokenRiskGoplusDTO();
        tokenRiskGoplusDTO.setDefaultAccountState(Integer.parseInt(solanaTokenDetails.getDefault_account_state()));
        tokenRiskGoplusDTO.setTrustedToken(solanaTokenDetails.getTrusted_token() != 0);
        tokenRiskGoplusDTO.setTotalSupply(solanaTokenDetails.getTotal_supply());
        if (solanaTokenDetails.getBalance_mutable_authority() != null) {
            tokenRiskGoplusDTO.setBalanceMutableAuthority(convertToJson(solanaTokenDetails.getBalance_mutable_authority()));
        }
        if (solanaTokenDetails.getClosable() != null) {
            tokenRiskGoplusDTO.setClosable(convertToJson(solanaTokenDetails.getClosable()));
        }
        if(solanaTokenDetails.getMintable() != null){
            tokenRiskGoplusDTO.setMintable(convertToJson(solanaTokenDetails.getMintable()));
        }
        if (solanaTokenDetails.getCreators() != null) {
            tokenRiskGoplusDTO.setCreator(convertToJson(solanaTokenDetails.getCreators()));
        }
        if (solanaTokenDetails.getDefault_account_state_upgradable() != null) {
            tokenRiskGoplusDTO.setDefaultAccountStateUpgradable(convertToJson(solanaTokenDetails.getDefault_account_state_upgradable()));
        }
        if (solanaTokenDetails.getDex() != null) {
            tokenRiskGoplusDTO.setDex(convertToJson(solanaTokenDetails.getDex()));
        }
        if (solanaTokenDetails.getFreezable() != null) {
            tokenRiskGoplusDTO.setFreezable(convertToJson(solanaTokenDetails.getFreezable()));
        }
        if (solanaTokenDetails.getHolders() != null) {
            tokenRiskGoplusDTO.setHolders(convertToJson(solanaTokenDetails.getHolders()));
        }
        if (solanaTokenDetails.getLp_holders() != null) {
            tokenRiskGoplusDTO.setLpHolders(convertToJson(solanaTokenDetails.getLp_holders()));
        }
        if (solanaTokenDetails.getMetadata() != null) {
            tokenRiskGoplusDTO.setMetadata(convertToJson(solanaTokenDetails.getMetadata()));
        }
        if (solanaTokenDetails.getMetadata_mutable() != null) {
            tokenRiskGoplusDTO.setMetadataMutable(convertToJson(solanaTokenDetails.getMetadata_mutable()));
        }
        if (solanaTokenDetails.getNon_transferable() != null) {
            tokenRiskGoplusDTO.setNonTransferable("1".equals(solanaTokenDetails.getNon_transferable()));
        }
        if (solanaTokenDetails.getTransfer_fee() != null && solanaTokenDetails.getTransfer_fee().getCurrent_fee_rate() != null) {
            tokenRiskGoplusDTO.setTransferFee(convertToJson(solanaTokenDetails.getTransfer_fee()));
        }
        if (solanaTokenDetails.getTransfer_fee_upgradable() != null) {
            tokenRiskGoplusDTO.setTransferFeeUpgradable(convertToJson(solanaTokenDetails.getTransfer_fee_upgradable()));
        }
        if(solanaTokenDetails.getTransfer_hook_upgradable() !=null){
            tokenRiskGoplusDTO.setTransferHookUpgradable(convertToJson(solanaTokenDetails.getTransfer_hook_upgradable()));
        }
        return tokenRiskGoplusDTO;
    }


    static String convertToJson(Object input) {
        try {
            if (input instanceof String) {
                return (String) input;
            } else {
                return objectMapper.writeValueAsString(input);
            }
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}